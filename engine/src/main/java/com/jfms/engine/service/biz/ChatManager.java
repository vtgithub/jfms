package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.model.*;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.model.RedisChannelEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by vahid on 4/3/18.
 */
@Component
public class ChatManager implements InitializingBean {

    @Autowired
    RedisAssist redisAssist;
    @Autowired
    RedisConverter redisConverter;
    @Autowired
    JFMSMessageConverter jfmsMessageConverter;
    @Autowired
    UserSessionRepository userSessionRepository;
    @Autowired
    MessageListener messageListener;

    Gson gson = new Gson();

    @Override
    public void afterPropertiesSet() throws Exception {
        messageListener.init(redisConverter, userSessionRepository);
        redisAssist.setMessageListener(messageListener);
    }


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

    public void deleteMessage(JFMSClientDeleteMessage jfmsClientDeleteMessage) {
        WebSocketSession session = userSessionRepository.getSession(jfmsClientDeleteMessage.getTo());
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

    public void sendIsTypingMessage(JFMSClientIsTypingMessage jfmsClientIsTypingMessage) {
        WebSocketSession session = userSessionRepository.getSession(jfmsClientIsTypingMessage.getTo());
        JFMSServerIsTypingMessage jfmsServerIsTypingMessage =
                jfmsMessageConverter.JFMSClientIsTypingToJFMSServerIsTyping(jfmsClientIsTypingMessage);
        try {
            session.sendMessage(new TextMessage(gson.toJson(jfmsServerIsTypingMessage)));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void updatePresenceTime(JFMSClientPingMessage jfmsClientPingMessage, WebSocketSession session) {
        //todo think about this
        redisAssist.changePresenceTime(jfmsClientPingMessage.getFrom(), System.currentTimeMillis());
        try {
            session.sendMessage(new TextMessage("{\"status\":\"ok\"}"));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
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