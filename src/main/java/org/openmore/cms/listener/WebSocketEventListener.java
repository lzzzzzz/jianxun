package org.openmore.cms.listener;

import org.openmore.cms.dto.api.UserDto;
import org.openmore.cms.entity.ChatMessage;
import org.openmore.cms.entity.JqxxPrincipal;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.MessageDataType;
import org.openmore.cms.entity.enums.MessageType;
import org.openmore.cms.service.UserService;
import org.openmore.cms.service.impl.ChatService;
import org.openmore.common.redisSubscribe.RedisListenerHandle;
import org.openmore.common.utils.WebsocketJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

@Component
public class WebSocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Value("${server.port}")
    private String serverPort;

    @Value("${redis.set.onlineUsers}")
    private String onlineUsers;

    @Value("${redis.channel.userStatus}")
    private String userStatus;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisListenerHandle redisListenerHandle;


    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;

    @Autowired
    private ChatService chatService;


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        InetAddress localHost;
        try {
            //String number = event.getUser().getName();
            localHost = Inet4Address.getLocalHost();
            LOGGER.info("Received a new web socket connection from:" + localHost.getHostAddress() + ":" + serverPort);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        JqxxPrincipal jqxxPrincipal = (JqxxPrincipal)headerAccessor.getUser();
        String userNumber = jqxxPrincipal.getUserNumber();
        User user = userService.getEntityByHomeNumber(userNumber);
        //创建当前缓存数据，方法结束销毁
        User cacheUser = new User();
        cacheUser.setNickName(user.getNickName());
        cacheUser.setHomeNumber(user.getHomeNumber());
        cacheUser.setJoinNum(user.getJoinNum());
        user.setStatus(false);
        user.setUpdatedTime(new Date());
        user.setJoinNum(null);
        userService.updateNoCache(user);
        if(null!=user) {
            LOGGER.info("User Disconnected : " + user.getNickName());
            try {
                if(StringUtils.isEmpty(cacheUser.getJoinNum())){
                    List<UserDto> users = userService.selectAll(null, null, null, null,
                            null, user.getHomeNumber(), null, null, null, null);
                    if(!CollectionUtils.isEmpty(users)){
                        chatService.sendLeveMessage(cacheUser, true);
                    }
                }else{

                    chatService.sendLeveMessage(cacheUser, false);
                }

                //从聊天缓存列表清除
                redisTemplate.opsForSet().remove(onlineUsers, user.getHomeNumber());
                //最后移除用户订阅频道，不然前面用户收不到通知消息
                redisMessageListenerContainer.removeMessageListener(redisListenerHandle, new PatternTopic(user.getHomeNumber()));
                LOGGER.debug("==>移除频道："+user.getHomeNumber());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }
}