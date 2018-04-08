package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSClientEditMessage;
import com.jfms.engine.api.model.JFMSClientLoginMessage;
import com.jfms.engine.api.model.JFMSClientSendMessage;
import com.jfms.engine.api.model.JFMSServerEditMessage;
import com.jfms.engine.dal.UserSessionRepository;
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

    public void sendMessage(JFMSClientSendMessage jfmsClientSendMessage) {

        String redisChannelEntityJson = gson.toJson(redisConverter.getRedisChannelEntity(jfmsClientSendMessage));
        redisAssist.sendMessage(
                getChannelName(jfmsClientSendMessage.getFrom() , jfmsClientSendMessage.getTo()),
                redisChannelEntityJson
        );
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

    public void init(JFMSClientLoginMessage jfmsClientLoginMessage, WebSocketSession session) {
        userSessionRepository.addSession(jfmsClientLoginMessage.getUserName(), session);
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