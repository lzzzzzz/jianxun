package org.openmore.common.interceptors;

import net.sf.json.JSONObject;
import org.openmore.cms.controller.api.ChatController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ChatHandler implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

        //连接关闭后的操作

        @Override

        public void afterConnectionClosed(WebSocketSession wsession, CloseStatus status) throws Exception {

            System.out.println("用户"+wsession.getAttributes().get("username")+"已经关闭");

        }

        //再handshake建立连接后，username存到map中了，wsession怎么拿到的map的值

        @Override

        public void afterConnectionEstablished(WebSocketSession wsession) throws Exception {

            String uname=(String) wsession.getPrincipal().getName();

            System.out.println("用户"+uname+"已经加入");
        }



        // message 是前台传过来的json数据，包括用户名，和发送的消息，要将消息转发给所有的其他用户,重新转发的话message格式和原来是一样的

        @Override

        public void handleMessage(WebSocketSession wsession, WebSocketMessage<?> message) throws Exception {

            //TextMessage tm=new TextMessage(message.toString());

            System.out.println("开始发送消息");

            //这是json字符串

            String jm=message.getPayload().toString();

            JSONObject json= JSONObject.fromObject(jm);

            //System.out.println(jm);
            LOGGER.debug("==>message:"+jm);
        }



        @Override

        public void handleTransportError(WebSocketSession wsession, Throwable th) throws Exception {

            if(wsession.isOpen()){

                wsession.close();

            }

        }



        @Override

        public boolean supportsPartialMessages() {

            return false;

        }



    }