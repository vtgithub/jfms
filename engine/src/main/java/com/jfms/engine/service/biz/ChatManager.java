package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.model.*;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.model.RedisChannelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by vahid on 4/3/18.
 */
@Component
public class ChatManager {

    @Autowired
    RedisAssist redisAssist;
    @Autowired
    RedisConverter redisConverter;
    @Autowired
    JFMSMessageConverter jfmsMessageConverter;
    @Autowired
    UserSessionRepository userSessionRepository;

    Gson gson = new Gson();

    public void init(JFMSClientLoginMessage jfmsClientLoginMessage, WebSocketSession session) {
        userSessionRepository.addSession(jfmsClientLoginMessage.getUserName(), session);
    }

    public void sendMessage(JFMSClientSendMessage jfmsClientSendMessage, WebSocketSession session) {
        RedisChannelEntity redisChannelEntity = redisConverter.getRedisChannelEntity(jfmsClientSendMessage);
        String redisChannelEntityJson = gson.toJson(redisChannelEntity);
        redisAssist.sendMessage(
                getChannelName(jfmsClientSendMessage.getFrom() , jfmsClientSendMessage.getTo()),
                redisChannelEntityJson
        );
        String messageIdJson = "{\"id\":\"" + redisChannelEntity.getId() + "\"}";
        try {
            session.sendMessage(new TextMessage(messageIdJson));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
        //todo save in message history
    }

    public void editMessage(JFMSClientEditMessage jfmsClientEditMessage) {
        WebSocketSession receiverSession = userSessionRepository.getSession(jfmsClientEditMessage.getTo());
        JFMSServerEditMessage jfmsServerEditMessage =
                jfmsMessageConverter.JFMSClientEditToJFMSServerEdit(jfmsClientEditMessage);
        try {
            receiverSession.sendMessage(new TextMessage(gson.toJson(jfmsServerEditMessage)));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
        //todo edit in message history
    }

    public void deleteMessage(JFMSClientDeleteMessage jfmsClientDeleteMessage, WebSocketSession session) {
        JFMSServerDeleteMessage jfmsServerDeleteMessage =
                jfmsMessageConverter.JFMSClientDeleteToJFMSServerDelete(jfmsClientDeleteMessage);
        try {
            session.sendMessage(new TextMessage(gson.toJson(jfmsServerDeleteMessage)));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
        //todo delete from history
    }

    public void removeUserSession(String sessionId) {
        userSessionRepository.removeBySession(sessionId);
    }

    //---------------------------------

    public String getChannelName(String from, String to){
        if (from.compareTo(to) >= 0)
            return from + to;
        else
            return to + from;

    }
}