package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSServerSendMessage;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.model.RedisChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;

@Component
public class MessageListener extends JedisPubSub {

    UserSessionRepository userSessionRepository;
    RedisConverter redisConverter;
    Gson gson = new Gson();

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        RedisChannelEntity redisChannelEntity = gson.fromJson(message, RedisChannelEntity.class);
        WebSocketSession session = userSessionRepository.getSession(redisChannelEntity.getTo());
        JFMSServerSendMessage jfmsServerSendMessage = redisConverter.getJFMSReceiveMessage(redisChannelEntity);
        try {
            session.sendMessage(new TextMessage(gson.toJson(jfmsServerSendMessage)));
        } catch (IOException e) {
            //todo log
            e.printStackTrace();
        }
    }

    public void init(RedisConverter redisConverter, UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
        this.redisConverter = redisConverter;
    }
}
