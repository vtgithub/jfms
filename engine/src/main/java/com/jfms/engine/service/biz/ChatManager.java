package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.engine.api.Method;
import com.jfms.engine.api.converter.JFMSMessageConverter;
import com.jfms.engine.api.model.*;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.remote.GroupConverter;
import com.jfms.engine.service.biz.remote.OnlineMessageListener;
import com.jfms.engine.service.biz.remote.api.*;
import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;
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
        JFMSServerSendMessage jfmsServerSendMessage =
                jfmsMessageConverter.clientSendToServerSend(messageId, jfmsClientSendMessage);
        sendOnlineOrOffline(jfmsClientSendMessage.getTo(), gson.toJson(jfmsServerSendMessage));
        try {
            session.sendMessage(new TextMessage("{\"id\":\"" + messageId + "\"}"));
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

    public void isTypingMessage(JFMSClientIsTypingMessage jfmsClientIsTypingMessage) {
        JFMSServerIsTypingMessage jfmsServerIsTypingMessage =
                jfmsMessageConverter.clientIsTypingToServerIsTyping(jfmsClientIsTypingMessage);
        sendOnline(jfmsClientIsTypingMessage.getTo(), gson.toJson(jfmsServerIsTypingMessage));
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
    //--------------- group messaging

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

        //todo group message History
        try {
            session.sendMessage(new TextMessage(groupId));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void sendGroupMessage(JFMSClientSendMessage jfmsClientGroupSendMessage, WebSocketSession session) {

        String messageId = UUID.randomUUID().toString();
        JFMSServerSendMessage jfmsServerSendMessage =
                jfmsMessageConverter.clientSendToServerSend(messageId, jfmsClientGroupSendMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupSendMessage.getFrom(),
                jfmsClientGroupSendMessage.getTo(),
                gson.toJson(jfmsServerSendMessage)
        );

        try {
            session.sendMessage(new TextMessage("{\"id\":\"" + messageId+ "\"}"));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
        //todo group message history
    }

    public void editGroupMessage(JFMSClientEditMessage jfmsClientGroupEditMessage){
        JFMSServerEditMessage jfmsServerGroupEditMessage =
                jfmsMessageConverter.clientEditToServerEdit(jfmsClientGroupEditMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupEditMessage.getFrom(),
                jfmsClientGroupEditMessage.getTo(),
                gson.toJson(jfmsServerGroupEditMessage)
        );
        //todo group message history
    }

    public void deleteGroupMessage(JFMSClientDeleteMessage jfmsClientGroupDeleteMessage){
        JFMSServerDeleteMessage jfmsServerGroupDeleteMessage =
                jfmsMessageConverter.clientDeleteToServerDelete(jfmsClientGroupDeleteMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupDeleteMessage.getFrom(),
                jfmsClientGroupDeleteMessage.getTo(),
                gson.toJson(jfmsServerGroupDeleteMessage)
        );
        //todo group delete message history
    }

    public void groupIsTypingMessage(JFMSClientIsTypingMessage jfmsClientGroupIsTypingMessage) {
        JFMSServerIsTypingMessage jfmsServerGroupIsTypingMessage =
                jfmsMessageConverter.clientIsTypingToServerIsTyping(jfmsClientGroupIsTypingMessage);
        sendGroupOnline(
                jfmsClientGroupIsTypingMessage.getFrom(),
                jfmsClientGroupIsTypingMessage.getTo(),
                gson.toJson(jfmsServerGroupIsTypingMessage)
        );
    }


    public void setGroupLeaveTime(JFMSClientConversationLeaveMessage jfmsClientGroupConversationLeaveMessage) {
        lastSeenRepository.setLastSeen(
                jfmsClientGroupConversationLeaveMessage.getFrom(),
                jfmsClientGroupConversationLeaveMessage.getTo(),
                jfmsClientGroupConversationLeaveMessage.getLeaveTime()
        );
        JFMSServerConversationMessage jfmsServerConversationMessage =
                jfmsMessageConverter.clientConversationLeaveToServerConversation(jfmsClientGroupConversationLeaveMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupConversationLeaveMessage.getFrom(),
                jfmsClientGroupConversationLeaveMessage.getTo(),
                gson.toJson(jfmsServerConversationMessage)
        );

    }

    //---------------------------------
    private void sendGroupCreationMessageToMembers(JFMSServerGroupCreationMessage jfmsServerGroupCreationMessage) {
        Iterator<Map.Entry<String, Boolean>> memberIterator = jfmsServerGroupCreationMessage.getJfmsGroupMemberMap().entrySet().iterator();
        while (memberIterator.hasNext()){
            Map.Entry<String, Boolean> member = memberIterator.next();
            sendOnlineOrOffline(member.getKey(), gson.toJson(jfmsServerGroupCreationMessage));

        }
    }

    private void sendGroupOnlineOrOffline(String from, String groupId, String message) {
        GroupInfoEntity groupInfoEntity = groupRepository.getGroupInfo(groupId);
        if (!groupInfoEntity.contains(from))
            return;
        Iterator<Map.Entry<String, Boolean>> groupIterator = groupInfoEntity.getJfmsGroupMemberMap().entrySet().iterator();
        while (groupIterator.hasNext()){
            Map.Entry<String, Boolean> next = groupIterator.next();
            sendOnlineOrOffline(next.getKey(), message);
        }
    }

    private void sendOnlineOrOffline(String user, String message) {
        String memberPresenceStatus = presenceRepository.getPresenceStatus(user);
        if (memberPresenceStatus == null || memberPresenceStatus.equals("offline")){
            OfflineMessage offlineMessage =
                    OfflineMessageApiClient.getOfflineMessage(user, message);
            offlineMessageApiClient.produceMessage(offlineMessage);
        }else {
            onlineMessageRepository.sendMessage(user, message);
        }
    }

    private void sendGroupOnline(String from, String groupId, String message) {
        GroupInfoEntity groupInfoEntity = groupRepository.getGroupInfo(groupId);
        if (!groupInfoEntity.contains(from))
            return;
        Iterator<Map.Entry<String, Boolean>> gIterator = groupInfoEntity.getJfmsGroupMemberMap().entrySet().iterator();
        while (gIterator.hasNext()){
            Map.Entry<String, Boolean> next = gIterator.next();
            sendOnline(next.getKey(), message);
        }
    }

    private void sendOnline(String user, String message){
        String memberPresenceStatus = presenceRepository.getPresenceStatus(user);
        if (memberPresenceStatus != null && memberPresenceStatus.equals("online")){
            onlineMessageRepository.sendMessage(user, message);
        }
    }


    public String getChannelName(String from, String to){
        if (from.compareTo(to) >= 0)
            return from + to;
        else
            return to + from;

    }
}