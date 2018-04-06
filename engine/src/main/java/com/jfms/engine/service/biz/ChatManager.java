package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSLoginMessage;
import com.jfms.engine.api.model.JFMSSendMessage;
import com.jfms.engine.dal.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

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
    UserSessionRepository userSessionRepository;

    public void sendMessage(JFMSSendMessage jfmsSendMessage) {
        String redisChannelEntityJson = (new Gson()).toJson(redisConverter.getRedisChannelEntity(jfmsSendMessage));
        redisAssist.sendMessage(
                getChannelName(jfmsSendMessage.getFrom() , jfmsSendMessage.getTo()),
                redisChannelEntityJson
        );
    }

    public void init(JFMSLoginMessage jfmsLoginMessage, WebSocketSession session) {
        userSessionRepository.addSession(jfmsLoginMessage.getUserName(), session);
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