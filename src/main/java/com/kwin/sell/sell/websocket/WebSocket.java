package com.kwin.sell.sell.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
//@ServerEndpoint("/webSocket")
@ServerEndpoint("/webSocket/{token}")
@Slf4j
public class WebSocket {

    private Session session;
    private String token;

//    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String, WebSocket>  webSocketSet = new ConcurrentHashMap<String, WebSocket>();

    @OnOpen
    public void onOpen(@PathParam (value = "token") String param, Session session) {
    	log.info(param);
        this.session = session;
        this.token = param;
//        webSocketSet.add(param, this);
        webSocketSet.put(param, this);
        log.info("【websocket消息】有新的连接, 总数:{}", webSocketSet.size());
    }

    @OnClose
    public void onClose() {
//        webSocketSet.remove(this);
    	webSocketSet.remove(token);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public void sendMessage(String message) {
        /*for (WebSocket webSocket : webSocketSet) {
            log.info("【websocket消息】广播消息, message={}", message);
            log.info("" + webSocket.session.getId() );
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    	
    	for (String token : webSocketSet.keySet()) {
            log.info("【websocket消息】广播消息, message={}", message);
            log.info("" + token );
            try {
            	webSocketSet.get(token).session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

