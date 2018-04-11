package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.converter.JFMSMessageConverter;
import com.jfms.engine.api.model.*;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.remote.OnlineMessageConverter;
import com.jfms.engine.service.biz.remote.OnlineMessageListener;
import com.jfms.engine.service.biz.remote.api.LastSeenRepository;
import com.jfms.engine.service.biz.remote.api.PresenceRepository;
import com.jfms.engine.service.biz.remote.api.PresenceRepositoryRedisImpl;
import com.jfms.engine.service.biz.remote.model.OnlineMessageEntity;
import com.jfms.engine.service.biz.remote.api.OnlineMessageRepository;
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
    OnlineMessageRepository onlineMessageRepository;
    @Autowired
    OnlineMessageConverter onlineMessageConverter;
    @Autowired
    PresenceRepository presenceRepository;
    @Autowired
    LastSeenRepository lastSeenRepository;
    @Autowired
    JFMSMessageConverter jfmsMessageConverter;
    @Autowired
    UserSessionRepository userSessionRepository;
    @Autowired
    OnlineMessageListener onlineMessageListener;

    Gson gson = new Gson();

    @Override
    public void afterPropertiesSet() throws Exception {
        onlineMessageListener.init(onlineMessageConverter, userSessionRepository);
        onlineMessageRepository.setMessageListener(onlineMessageListener);
    }


    public void init(JFMSClientLoginMessage jfmsClientLoginMessage, WebSocketSession session) {
        userSessionRepository.addSession(jfmsClientLoginMessage.getUserName(), session);
    }

    public void sendMessage(JFMSClientSendMessage jfmsClientSendMessage, WebSocketSession session) {
        OnlineMessageEntity onlineMessageEntity = onlineMessageConverter.getOnlineMessageEntity(jfmsClientSendMessage);
        String redisChannelEntityJson = gson.toJson(onlineMessageEntity);
        onlineMessageRepository.sendMessage(
                getChannelName(jfmsClientSendMessage.getFrom() , jfmsClientSendMessage.getTo()),
                redisChannelEntityJson
        );
        String messageIdJson = "{\"id\":\"" + onlineMessageEntity.getId() + "\"}";
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
        presenceRepository.changePresenceTime(jfmsClientPingMessage.getFrom(), System.currentTimeMillis());
        try {
            session.sendMessage(new TextMessage("{\"status\":\"ok\"}"));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void setLeaveTime(JFMSClientConversationLeaveMessage jfmsClientConversationLeaveMessage) {
        String room =
                getChannelName(jfmsClientConversationLeaveMessage.getFrom(), jfmsClientConversationLeaveMessage.getTo());
        lastSeenRepository.setLastSeen(room, jfmsClientConversationLeaveMessage.getLeaveTime());
        JFMSServerConversationMessage jfmsServerConversationMessage =
                jfmsMessageConverter.JFMSClientConversationLeaveToJFMSServerConversation(jfmsClientConversationLeaveMessage);
        WebSocketSession session = userSessionRepository.getSession(jfmsClientConversationLeaveMessage.getTo());
        try {
            session.sendMessage(new TextMessage(gson.toJson(jfmsServerConversationMessage)));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void getLeaveTime(JFMSClientConversationInMessage jfmsClientConversationInMessage, WebSocketSession session) {
        String room = getChannelName(jfmsClientConversationInMessage.getFrom(), jfmsClientConversationInMessage.getTo());
        Long leaveTime = lastSeenRepository.getLastSeen(room);

        try {
            session.sendMessage(
                    new TextMessage(
                        gson.toJson(new JFMSServerConversationMessage(jfmsClientConversationInMessage.getTo(), leaveTime))
                    )
            );
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