package org.openmore.common.interceptors;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthHandshakeInterceptor.class);

    /**在websocket握手前判断，判断当前用户是否已经登录。如果未登录，则不允许登录websocket*/
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if(!SecurityUtils.getSubject().isAuthenticated()){
            log.error("未登录系统，禁止登录websocket!");
            return false;
        }else{
            try{
                // 获得登录信息
                Subject subject = SecurityUtils.getSubject();
                //如果用户已经登录，获得登录信息，保存起来
                if (subject != null && subject.isAuthenticated()) {
                    String loginName = (String) SecurityUtils.getSubject().getPrincipal();
                    String userString = (String) SecurityUtils.getSubject().getSession().getAttribute(loginName);
                    User user = JSONObject.parseObject(userString, User.class);
                    if (null != user) {
                        ThreadLocalConfig.setUser(user);
                    }
                }
            } catch (Exception e) {
                return false;
            }
            Boolean status = ThreadLocalConfig.getUser().getStatus();
            if(null!=status && status){
                log.error("已连接服务器，请不要重复连接");
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }

    // 参考 HttpSessionHandshakeInterceptor
    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}