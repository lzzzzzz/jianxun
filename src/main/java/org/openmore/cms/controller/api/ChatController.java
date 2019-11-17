package org.openmore.cms.controller.api;

import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.dto.api.UserDto;
import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.entity.ChatMessage;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.MessageDataType;
import org.openmore.cms.entity.enums.MessageType;
import org.openmore.cms.service.UserService;
import org.openmore.cms.service.impl.ChatService;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.common.utils.RSAEncrypt;
import org.openmore.common.utils.WebsocketJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "/Chat", tags = "Chat", description = "消息处理器")
@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Value("${redis.channel.msgToAll}")
    private String msgToAll;

    @Value("${redis.set.onlineUsers}")
    private String onlineUsers;

    @Value("${redis.channel.userStatus}")
    private String userStatus;

    /**客户端KEY*/
    @Value("${websocket.client.rsa.privateKey}")
    private String privateKey;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ChatService chatService;


    /**连接websocket服务器*/
    @TokenAuthCheck
    @MessageMapping("/chat.connect")
    public BaseResponse addUser(SimpMessageHeaderAccessor headerAccessor) {

        LOGGER.info("User added in Chat");
        try {
            String userNumber = headerAccessor.getUser().getName();
            InetAddress localHost = Inet4Address.getLocalHost();
            User user = userService.getEntityByHomeNumber(userNumber);
            //先判断是否未连接shiro服务器
            if(!user.getStatus()){
                LOGGER.error("==>400请先连接服务器");
                return BaseResponse.buildFailResponse(4002, "请先连接服务器");
            }
            //判断ip地址是否异常
            if(user.getIpAddress().equals(localHost.getHostAddress())){
                LOGGER.error("==>400IP地址不匹配");
                return BaseResponse.buildFailResponse(400, "IP地址不匹配");
            }
            //加入在线队列
            redisTemplate.opsForSet().add(onlineUsers, userNumber);
            //发送成功则通知用户自己的房间ID
            return BaseResponse.buildSuccessResponse("连接成功");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("==>400连接出错");
            return BaseResponse.buildFailResponse(400, "连接出错");
        }
    }

    /**websocket加入房间-弃用*/
    @MessageMapping("/chat.join")
    public BaseResponse join(@Payload String number, SimpMessageHeaderAccessor headerAccessor) {
        try {
            if(StringUtils.isEmpty(number)){
                return BaseResponse.buildFailResponse(400, "加入房间号不能为空");
            }
            String senderNumber = headerAccessor.getUser().getName();
            if(StringUtils.isEmpty(senderNumber) || redisTemplate.opsForSet().isMember(onlineUsers, senderNumber)){
                return BaseResponse.buildFailResponse(4002, "未连接服务器");
            }
            return BaseResponse.buildSuccessResponse("加入成功");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("==>400加入房间出错");
            return BaseResponse.buildFailResponse(400, "加入房间出错");
        }
    }

    /**发送消息*/
    //@TokenAuthCheck
    @MessageMapping("/chat.sendMessage")
    public BaseResponse sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        try {
            if(null == chatMessage){
                return BaseResponse.buildFailResponse(400, "消息不能为空");
            }
            if(StringUtils.isEmpty(chatMessage.getContent())){
                return BaseResponse.buildFailResponse(400, "消息内容不能为空");
            }
            if(StringUtils.isEmpty(chatMessage.getSenderTo())){
                return BaseResponse.buildFailResponse(400, "消息接收人不能为空");
            }
            String senderNumber = headerAccessor.getUser().getName();
            if(StringUtils.isEmpty(senderNumber) || redisTemplate.opsForSet().isMember(onlineUsers, senderNumber)){
                LOGGER.error("==>4002请先连接服务器");
                return BaseResponse.buildFailResponse(4002, "请先连接服务器");
            }
            /**数据解密*/
            //chatMessage.setContent(RSAEncrypt.privateDecrypt(chatMessage.getContent(), RSAEncrypt.getPrivateKey(privateKey)));
            chatMessage.setContent(chatMessage.getContent());
            chatMessage.setType(MessageType.CHAT);
            chatMessage.setDatatype(MessageDataType.TEXT);
            chatMessage.setSender(headerAccessor.getUser().getName());
            LOGGER.debug("==>chatMessage:"+chatMessage.toString());
            List<UserDto> userList = userService.selectAll(null, null, null,null,
                    null, chatMessage.getSenderTo(), null, null, null, null);
            //发送通知消息有人加入房间
            chatService.sendMessage(chatMessage.getSenderTo(), chatMessage);
            chatService.sendMessageUserDtos(userList, chatMessage);
            return BaseResponse.buildSuccessResponse("发送成功");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("==>4002发送消息出错");
            return BaseResponse.buildFailResponse(400, "发送消息出错");
        }
    }

}