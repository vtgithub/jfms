package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.engine.api.Method;
import com.jfms.engine.api.converter.JFMSMessageConverter;
import com.jfms.engine.api.model.*;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.remote.GroupConverter;
import com.jfms.engine.service.biz.remote.OnlineMessageConverter;
import com.jfms.engine.service.biz.remote.OnlineMessageListener;
import com.jfms.engine.service.biz.remote.api.*;
import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;
import com.jfms.engine.service.biz.remote.model.OnlineMessageEntity;
import com.jfms.message_history.model.P2PMessage;
import com.jfms.offline_message.model.OfflineMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

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
    @Autowired
    OfflineMessageApiClient offlineMessageApiClient;
    @Autowired
    MessageHistoryApiClient messageHistoryApiClient;
    @Autowired
    GroupApiClient groupApiClient;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupConverter groupConverter;
    Gson gson = new Gson();

    @Override
    public void afterPropertiesSet() throws Exception {
        onlineMessageListener.init(
                onlineMessageConverter,
                userSessionRepository
        );
        onlineMessageRepository.setMessageListener(onlineMessageListener);
    }


    public void init(JFMSClientLoginMessage jfmsClientLoginMessage, WebSocketSession session) {
        userSessionRepository.addSession(jfmsClientLoginMessage.getUserName(), session);
        presenceRepository.setPresenceStatus(jfmsClientLoginMessage.getUserName(), UserStatus.ONLINE.getValue());
        List<String> offlineMessageList =
                offlineMessageApiClient.consumeMessage(jfmsClientLoginMessage.getUserName());
        String offlineMessageListInJson = gson.toJson(offlineMessageList, List.class);
        try {
            session.sendMessage(new TextMessage(offlineMessageListInJson));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void sendMessage(JFMSClientSendMessage jfmsClientSendMessage, WebSocketSession session) {
        String messageId = UUID.randomUUID().toString();
        String receiverStatus= presenceRepository.getPresenceStatus(jfmsClientSendMessage.getTo());
        if (receiverStatus == null || receiverStatus.equals(UserStatus.OFFLINE.getValue())){
            OfflineMessage offlineMessage = OfflineMessageApiClient.getOfflineMessage(
                    jfmsClientSendMessage.getTo(),
                    gson.toJson(jfmsMessageConverter.clientSendToServerSend(messageId, jfmsClientSendMessage))
            );
            offlineMessageApiClient.produceMessage(offlineMessage);
        }else{
            OnlineMessageEntity onlineMessageEntity = onlineMessageConverter.getOnlineMessageEntity(messageId, jfmsClientSendMessage);
            String redisChannelEntityJson = gson.toJson(onlineMessageEntity);
            onlineMessageRepository.sendMessage(
                    getChannelName(jfmsClientSendMessage.getFrom() , jfmsClientSendMessage.getTo()),
                    redisChannelEntityJson
            );
        }
        String messageIdJson = "{\"id\":\"" + messageId + "\"}";
        try {
            session.sendMessage(new TextMessage(messageIdJson));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
        P2PMessage p2PMessage = MessageHistoryApiClient.getP2PMessage(
                messageId,
                jfmsClientSendMessage.getFrom(),
                jfmsClientSendMessage.getBody(),
                jfmsClientSendMessage.getSubject(),
                jfmsClientSendMessage.getSendTime()
        );
        messageHistoryApiClient.saveHistoryMessage(jfmsClientSendMessage.getTo(), p2PMessage);
    }

    public void editMessage(JFMSClientEditMessage jfmsClientEditMessage) {
        JFMSServerEditMessage jfmsServerEditMessage =
                jfmsMessageConverter.clientEditToServerEdit(jfmsClientEditMessage);

        sendOnlineOrOffline(jfmsClientEditMessage.getTo(), gson.toJson(jfmsServerEditMessage));

        P2PMessage p2PMessage = MessageHistoryApiClient.getP2PMessage(
                jfmsClientEditMessage.getId(),
                jfmsClientEditMessage.getFrom(),
                jfmsClientEditMessage.getBody(),
                jfmsClientEditMessage.getSubject(),
                jfmsClientEditMessage.getEditTime()
        );
        messageHistoryApiClient.UpdateHistoryMessage(jfmsClientEditMessage.getTo(), p2PMessage);
    }

    public void deleteMessage(JFMSClientDeleteMessage jfmsClientDeleteMessage) {
        JFMSServerDeleteMessage jfmsServerDeleteMessage =
                jfmsMessageConverter.clientDeleteToServerDelete(jfmsClientDeleteMessage);

        sendOnlineOrOffline(jfmsClientDeleteMessage.getTo(), gson.toJson(jfmsServerDeleteMessage));

        messageHistoryApiClient.deleteP2PMessage(
                jfmsClientDeleteMessage.getTo(),
                Arrays.asList(jfmsClientDeleteMessage.getId())
        );
    }

    public void sendIsTypingMessage(JFMSClientIsTypingMessage jfmsClientIsTypingMessage) {
        WebSocketSession session = userSessionRepository.getSession(jfmsClientIsTypingMessage.getTo());
        JFMSServerIsTypingMessage jfmsServerIsTypingMessage =
                jfmsMessageConverter.clientIsTypingToServerIsTyping(jfmsClientIsTypingMessage);
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
        lastSeenRepository.setLastSeen(
                jfmsClientConversationLeaveMessage.getFrom(),
                jfmsClientConversationLeaveMessage.getTo(),
                jfmsClientConversationLeaveMessage.getLeaveTime()
        );
        JFMSServerConversationMessage jfmsServerConversationMessage =
                jfmsMessageConverter.clientConversationLeaveToServerConversation(jfmsClientConversationLeaveMessage);
        sendOnlineOrOffline(jfmsClientConversationLeaveMessage.getTo(), gson.toJson(jfmsServerConversationMessage));

    }

    public void getLeaveTime(JFMSClientConversationInMessage jfmsClientConversationInMessage, WebSocketSession session) {
        Long leaveTime = lastSeenRepository.getLastSeen(
                jfmsClientConversationInMessage.getFrom(),
                jfmsClientConversationInMessage.getTo()
        );
        try {
            session.sendMessage(
                    new TextMessage(
                        gson.toJson(new JFMSServerConversationMessage(
                                Method.CONVERSATION_LEAVE.getValue(),
                                jfmsClientConversationInMessage.getTo(),
                                leaveTime
                        ))
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void setSeen(JFMSClientSeenMessage jfmsClientSeenMessage) {
        WebSocketSession session = userSessionRepository.getSession(jfmsClientSeenMessage.getTo());
        JFMSServerSeenMessage jfmsServerSeenMessage = jfmsMessageConverter.clientSeentoServerSeen(jfmsClientSeenMessage);
        try {
            session.sendMessage(new TextMessage(gson.toJson(jfmsServerSeenMessage)));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void removeUserSession(String sessionId) {
        userSessionRepository.removeBySession(sessionId, presenceRepository);
//        presenceRepository.setPresenceStatus(jfmsClientLoginMessage.getUserName(), UserStatus.ONLINE.getValue());

    }

    public void createGroup(JFMSClientGroupCreationMessage jfmsClientGroupCreationMessage, WebSocketSession session) {
        GroupInfo groupInfo = GroupApiClient.getGroupInfo(
            jfmsClientGroupCreationMessage.getDisplayName(),
                jfmsClientGroupCreationMessage.getCreator(),
                jfmsClientGroupCreationMessage.getJfmsGroupMemberMap(),
                jfmsClientGroupCreationMessage.getType()
        );
        String groupId = groupApiClient.addGroup(groupInfo);
        GroupInfoEntity groupInfoEntity = groupConverter.getEntityFromJFMSMessage(jfmsClientGroupCreationMessage);
        groupRepository.saveGroupInfo(groupId, groupInfoEntity);
        sendGroupCreationMessageToMembers(
                jfmsMessageConverter.clientGroupCreationToServerGroupCreation(groupId, jfmsClientGroupCreationMessage)
        );
        try {
            session.sendMessage(new TextMessage(groupId));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void sendGroupMessage(JFMSClientSendMessage jfmsClientGroupSendMessage, WebSocketSession session) {
        fdf//todo impl
    }

    //---------------------------------

    private void sendGroupCreationMessageToMembers(JFMSServerGroupCreationMessage jfmsServerGroupCreationMessage) {
        Iterator<Map.Entry<String, Boolean>> memberIterator = jfmsServerGroupCreationMessage.getJfmsGroupMemberMap().entrySet().iterator();
        while (memberIterator.hasNext()){
            Map.Entry<String, Boolean> member = memberIterator.next();
            sendOnlineOrOffline(member.getKey(), gson.toJson(jfmsServerGroupCreationMessage));

        }
    }

    private void sendOnlineOrOffline(String user, String message) {
        String memberPresenceStatus = presenceRepository.getPresenceStatus(user);
        if (memberPresenceStatus == null || memberPresenceStatus.equals("offline")){
            OfflineMessage offlineMessage =
                    OfflineMessageApiClient.getOfflineMessage(user, message);
            offlineMessageApiClient.produceMessage(offlineMessage);
        }else {
            WebSocketSession userSession = userSessionRepository.getSession(user);
            try {
                userSession.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
                //todo log
            }
        }
    }


    public String getChannelName(String from, String to){
        if (from.compareTo(to) >= 0)
            return from + to;
        else
            return to + from;

    }
}