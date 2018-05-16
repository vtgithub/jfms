package com.jfms.engine.service.biz.remote;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSServerSendMessage;
import com.jfms.engine.dal.UserSessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;

@Component
public class OnlineMessageListener extends JedisPubSub {

    UserSessionRepository userSessionRepository;
//    OfflineMessageConverter offlineMessageConverter;
//    OfflineMessageApiClient offlineMessageApiClient;

//    Gson gson = new Gson();
    @Override
    public void onPMessage(String pattern, String channel, String message) {
//        OnlineMessageEntity onlineMessageEntity = gson.fromJson(message, OnlineMessageEntity.class);
        WebSocketSession session = userSessionRepository.getSession(channel);
        if (session != null) {
//            JFMSServerSendMessage jfmsServerSendMessage = onlineMessageConverter.getJFMSReceiveMessage(onlineMessageEntity);
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                //todo log
                e.printStackTrace();
            }
        }
    }

    public void init(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

}
