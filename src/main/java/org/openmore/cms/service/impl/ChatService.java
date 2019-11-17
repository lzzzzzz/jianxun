package org.openmore.cms.service.impl;

import org.openmore.cms.dto.api.UserDto;
import org.openmore.cms.entity.ChatMessage;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.MessageDataType;
import org.openmore.cms.entity.enums.MessageType;
import org.openmore.cms.service.UserService;
import org.openmore.common.utils.RSAEncrypt;
import org.openmore.common.utils.WebsocketJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    /**客户端KEY*/
    @Value("${websocket.rsa.publicKey}")
    private String publicKey;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    private UserService userService;

    /*websosket发送消息供redis消息池反射调用发送消息*/
    public void sendMsg(@Payload ChatMessage chatMessage, String channel) {
        LOGGER.info("Send msg by simpMessageSendingOperations:" + chatMessage.toString());
        try{
            //chatMessage.setContent(RSAEncrypt.publicEncrypt(chatMessage.getContent(), RSAEncrypt.getPublicKey(publicKey)));
            chatMessage.setContent(chatMessage.getContent());
            simpMessageSendingOperations.convertAndSend("/topic/"+channel, chatMessage);
        }catch (Exception e){
            LOGGER.error("==>sen message error:"+e.getMessage());
        }
    }

    /**单发消息*/
    public void sendMessage(String channel, ChatMessage chatMessage){
            redisTemplate.convertAndSend(channel, WebsocketJsonUtil.parseObjToJson(chatMessage));
    }
    /**单发消息*/
    public void sendMessageUser(User user, ChatMessage chatMessage){
        if(null == user || null == chatMessage){
            return;
        }
        if(user.getStatus()){
            redisTemplate.convertAndSend(user.getHomeNumber(), WebsocketJsonUtil.parseObjToJson(chatMessage));
        }
    }
    /**单发消息*/
    public void sendMessageUserDto(UserDto userDto, ChatMessage chatMessage){
        if(null == userDto || null == chatMessage){
            return;
        }
        if(userDto.getStatus()){
            redisTemplate.convertAndSend(userDto.getHomeNumber(), WebsocketJsonUtil.parseObjToJson(chatMessage));
        }
    }
    /**群发消息*/
    public void sendMessageUsers(List<User> users, ChatMessage chatMessage){
        if(!CollectionUtils.isEmpty(users)){
            for (User user: users){
                if(user.getStatus()){
                    redisTemplate.convertAndSend(user.getHomeNumber(), WebsocketJsonUtil.parseObjToJson(chatMessage));
                }
            }
        }
    }
    /**群发消息*/
    public void sendMessageUserDtos(List<UserDto> userDtos, ChatMessage chatMessage){
        if(!CollectionUtils.isEmpty(userDtos)){
            for (UserDto user: userDtos){
                if(user.getStatus()){
                    redisTemplate.convertAndSend(user.getHomeNumber(), WebsocketJsonUtil.parseObjToJson(chatMessage));
                }
            }
        }
    }

    /**发送加入房间消息*/
    public void sendJoinMessage(User user, boolean isMaster){
        String homeNumber = isMaster?user.getHomeNumber(): user.getJoinNum();
        //发送当前在线人数消息
        Long countOnline = userService.selectAllCount(null, null, null, null,
                null, homeNumber, true, null, null, null);
        //当前离线人数
        Long countOffline = userService.selectAllCount(null, null, null, null,
                null, homeNumber, false, null, null, null);
        countOnline = null == countOnline ? 0 : countOnline;
        countOffline = null == countOffline ? 0 : countOffline;
        if(!isMaster){
            User masterUser = userService.getEntityByHomeNumber(homeNumber);
            if(masterUser.getStatus()){
                countOnline +=1;
            }else{
                countOffline += 1;
            }
        }else{
            countOnline += 1;
        }

        //发送通知消息有人加入房间
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(MessageType.JOIN);
        chatMessage.setDatatype(MessageDataType.TEXT);
        if(isMaster){
            chatMessage.setContent("群主:[" + user.getNickName()+"]已上线，现在可收发消息");
        }else{
            chatMessage.setContent("[" + user.getNickName() + "]加入房间");
        }

        //当前房间人数
        chatMessage.setSender(countOnline + "/" + (countOnline + countOffline));
        chatMessage.setSenderTo(user.getHomeNumber());

        //通知房主
        sendMessage(homeNumber, chatMessage);
        //通知其它人
        List<UserDto> users = userService.selectAll(null, null, null, null,
                null, homeNumber, null, null, null, null);
        if (!CollectionUtils.isEmpty(users)) {
            //发送加入房间消息
            sendMessageUserDtos(users, chatMessage);
        }
    }

    /**发送离开消息*/
    public void sendLeveMessage(User user, boolean isMaster){
        String homeNumber = isMaster?user.getHomeNumber():user.getJoinNum();

        //发送当前在线人数消息
        Long countOnline = userService.selectAllCount(null, null, null, null,
                null, homeNumber, true, null, null, null);
        //当前离线人数
        Long countOffline = userService.selectAllCount(null, null, null, null,
                null, homeNumber, false, null, null, null);
        countOnline = null == countOnline?0:countOnline;
        countOffline = null == countOffline?0:countOffline;
        if(!isMaster){
            User masterUser = userService.getEntityByHomeNumber(homeNumber);
            if(masterUser.getStatus()){
                countOnline +=1;
            }else{
                countOffline += 1;
            }
        }else{
            countOffline += 1;
        }

        ChatMessage chatMessage = new ChatMessage();
        //发送通知消息有人离开房间
        chatMessage.setType(MessageType.LEAVE);
        chatMessage.setDatatype(MessageDataType.TEXT);
        String messageStr = "[" + user.getNickName()+"]暂时离开房间";
        if(isMaster){
            messageStr = "群主" + messageStr+ "，您现在无法收发消息";
        }
        chatMessage.setContent(messageStr);

        //当前房间人数
        chatMessage.setSender(countOnline+"/"+(countOnline+ countOffline));
        chatMessage.setSenderTo(user.getHomeNumber());

        //需要通知的人
        List<UserDto> userList = null;
        userList = userService.selectAll(null, null, null,null,
                    null, homeNumber, null, null, null, null);
        //群主单独通知
        if(!isMaster){
            sendMessage(homeNumber, chatMessage);
        }
        //发送消息
        //通知其它人员，此人已离开
        if(!CollectionUtils.isEmpty(userList)){
            sendMessageUserDtos(userList, chatMessage);
        }
    }
}