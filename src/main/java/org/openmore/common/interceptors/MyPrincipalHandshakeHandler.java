package org.openmore.common.interceptors;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.entity.JqxxPrincipal;
import org.openmore.cms.entity.User;
import org.openmore.cms.service.UserService;
import org.openmore.common.redisSubscribe.RedisListenerHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Component
public class MyPrincipalHandshakeHandler extends DefaultHandshakeHandler {
    private static final Logger log = LoggerFactory.getLogger(MyPrincipalHandshakeHandler.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisListenerHandle redisListenerHandle;


    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if(!SecurityUtils.getSubject().isAuthenticated()){
            log.error("未登录系统，禁止登录websocket!");
            return null;
        }else{
            String userHomeNumber = ThreadLocalConfig.getUser().getHomeNumber();
            User newUser = userService.getEntityByHomeNumber(userHomeNumber);

            Boolean status = newUser.getStatus();
            if(null!=status && status){
                log.error("已连接服务器，请不要重复连接");
                //添加用户订阅频道
                redisMessageListenerContainer.addMessageListener(redisListenerHandle, new PatternTopic(newUser.getHomeNumber()));
                log.debug("==>添加频道："+newUser.getHomeNumber());
                JqxxPrincipal jqxxPrincipal = new JqxxPrincipal(newUser.getHomeNumber(), newUser.getNickName());
                return jqxxPrincipal;
            }else{
                newUser.setStatus(true);
                userService.update(newUser);
                //添加用户订阅频道
                redisMessageListenerContainer.addMessageListener(redisListenerHandle, new PatternTopic(newUser.getHomeNumber()));
                log.debug("==>添加频道："+newUser.getHomeNumber());
                JqxxPrincipal jqxxPrincipal = new JqxxPrincipal(newUser.getHomeNumber(), newUser.getNickName());
                return jqxxPrincipal;
            }
        }
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}