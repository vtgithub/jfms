package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.engine.api.converter.JFMSMessageConverter;
import com.jfms.engine.api.model.*;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.remote.GroupConverter;
import com.jfms.engine.service.biz.remote.OnlineMessageListener;
import com.jfms.engine.service.biz.remote.api.*;
import com.jfms.engine.service.biz.remote.api.message_history.MessageHistoryGroupApiClient;
import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;
import com.jfms.message_history.model.HistoryMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Component
public class GroupChatManager implements InitializingBean{

    @Autowired
    UserSessionRepository userSessionRepository;
    @Autowired
    OnlineMessageListener onlineMessageListener;
    @Autowired
    OnlineMessageRepository onlineMessageRepository;
    @Autowired
    GroupApiClient groupApiClient;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GroupConverter groupConverter;
    @Autowired
    JFMSMessageConverter jfmsMessageConverter;
    @Autowired
    MessageHistoryApiFactory messageHistoryApiFactory;
    @Autowired
    LastSeenRepository lastSeenRepository;
    @Autowired
    PresenceRepository presenceRepository;
    @Autowired
    P2PChatManager p2PChatManager;
    Gson gson = new Gson();

    @Override
    public void afterPropertiesSet() throws Exception {
        onlineMessageListener.init(
                userSessionRepository
        );
        onlineMessageRepository.setMessageListener(onlineMessageListener);
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
        sendGroupCreationOrInfoUpdateMessageToMembers(
                jfmsMessageConverter.clientGroupCreationToServerGroupCreation(groupId, jfmsClientGroupCreationMessage)
        );

        //todo group message History
        try {
            session.sendMessage(new TextMessage("{\"goupId\":\"" + groupId+ "\"}"));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void editGroupInfo(JFMSClientGroupInfoEditMessage jfmsClientGroupInfoEditMessage){
        GroupInfo groupInfo = GroupApiClient.getGroupInfo(
                jfmsClientGroupInfoEditMessage.getId(),
                jfmsClientGroupInfoEditMessage.getDisplayName(),
                jfmsClientGroupInfoEditMessage.getCreator(),
                jfmsClientGroupInfoEditMessage.getJfmsGroupMemberMap(),
                jfmsClientGroupInfoEditMessage.getType()
        );
        groupApiClient.editGroup(groupInfo);
        GroupInfoEntity groupInfoEntity = groupConverter.getEntityFromJFMSMessage(jfmsClientGroupInfoEditMessage);
        groupRepository.saveGroupInfo(groupInfo.getId(), groupInfoEntity);
        sendGroupCreationOrInfoUpdateMessageToMembers(
                jfmsMessageConverter.clientGroupInfoEditToServerGroupCreation(jfmsClientGroupInfoEditMessage)
        );
    }


    public void sendGroupMessage(JFMSClientSendMessage jfmsClientGroupSendMessage, WebSocketSession session) {
        String messageId = UUID.randomUUID().toString();
        JFMSServerGroupSendMessage jfmsServerGroupSendMessage =
                jfmsMessageConverter.clientSendToServerGroupSend(messageId, jfmsClientGroupSendMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupSendMessage.getFrom(),
                jfmsClientGroupSendMessage.getTo(),
                gson.toJson(jfmsServerGroupSendMessage)
        );

        try {
            session.sendMessage(new TextMessage("{\"id\":\"" + messageId+ "\"}"));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
        HistoryMessage groupHistoryMessage = MessageHistoryGroupApiClient.getGroupHistoryMessage(
                messageId,
                jfmsClientGroupSendMessage.getFrom(),
                jfmsClientGroupSendMessage.getBody(),
                jfmsClientGroupSendMessage.getSubject(),
                jfmsClientGroupSendMessage.getSendTime()
        );
        messageHistoryApiFactory.getGroupApi().saveGroupHistoryMessage(jfmsClientGroupSendMessage.getTo(), groupHistoryMessage);
    }

    public void editGroupMessage(JFMSClientEditMessage jfmsClientGroupEditMessage){
        JFMSServerGroupEditMessage jfmsServerGroupEditMessage=
                jfmsMessageConverter.clientEditToServerGroupEdit(jfmsClientGroupEditMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupEditMessage.getFrom(),
                jfmsClientGroupEditMessage.getTo(),
                gson.toJson(jfmsServerGroupEditMessage)
        );
        HistoryMessage groupHistoryMessage = MessageHistoryGroupApiClient.getGroupHistoryMessage(
                jfmsClientGroupEditMessage.getId(),
                jfmsClientGroupEditMessage.getFrom(),
                jfmsClientGroupEditMessage.getBody(),
                jfmsClientGroupEditMessage.getSubject(),
                jfmsClientGroupEditMessage.getEditTime()
        );
        messageHistoryApiFactory.getGroupApi().updateGroupHistoryMessage(jfmsClientGroupEditMessage.getTo(), groupHistoryMessage);
    }

    public void deleteGroupMessage(JFMSClientDeleteMessage jfmsClientGroupDeleteMessage){
        JFMSServerGroupDeleteMessage jfmsServerGroupDeleteMessage=
                jfmsMessageConverter.clientDeleteToServerGroupDelete(jfmsClientGroupDeleteMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupDeleteMessage.getFrom(),
                jfmsClientGroupDeleteMessage.getTo(),
                gson.toJson(jfmsServerGroupDeleteMessage)
        );
        messageHistoryApiFactory.getGroupApi().deleteGroupMessage(
                jfmsClientGroupDeleteMessage.getTo(),
                jfmsClientGroupDeleteMessage.getIdList()
        );
    }

    public void groupIsTypingMessage(JFMSClientIsTypingMessage jfmsClientGroupIsTypingMessage) {
        JFMSServerGroupIsTypingMessage jfmsServerGroupIsTypingMessage =
                jfmsMessageConverter.clientIsTypingToServerGroupIsTyping(jfmsClientGroupIsTypingMessage);
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
        JFMSServerGroupConversationMessage jfmsServerGroupConversationMessage =
                jfmsMessageConverter.clientConversationLeaveToServerGroupConversation(jfmsClientGroupConversationLeaveMessage);
        sendGroupOnlineOrOffline(
                jfmsClientGroupConversationLeaveMessage.getFrom(),
                jfmsClientGroupConversationLeaveMessage.getTo(),
                gson.toJson(jfmsServerGroupConversationMessage)
        );

    }

    //-----------------------------

    private void sendGroupCreationOrInfoUpdateMessageToMembers(JFMSServerGroupCreationMessage jfmsServerGroupCreationMessage) {
        Iterator<Map.Entry<String, Boolean>> memberIterator = jfmsServerGroupCreationMessage.getJfmsGroupMemberMap().entrySet().iterator();
        while (memberIterator.hasNext()){
            Map.Entry<String, Boolean> member = memberIterator.next();
            p2PChatManager.sendOnlineOrOffline(member.getKey(), gson.toJson(jfmsServerGroupCreationMessage));

        }
    }

    private void sendGroupOnline(String from, String groupId, String message) {
        GroupInfoEntity groupInfoEntity = groupRepository.getGroupInfo(groupId);
        if (!groupInfoEntity.contains(from))
            return;
        Iterator<Map.Entry<String, Boolean>> gIterator = groupInfoEntity.getJfmsGroupMemberMap().entrySet().iterator();
        while (gIterator.hasNext()){
            Map.Entry<String, Boolean> next = gIterator.next();
            if (!next.getKey().equals(from))
                p2PChatManager.sendOnline(next.getKey(), message);
        }
    }

    private void sendGroupOnlineOrOffline(String from, String groupId, String message) {
        GroupInfoEntity groupInfoEntity = groupRepository.getGroupInfo(groupId);
        if (!groupInfoEntity.getJfmsGroupMemberMap().containsKey(from))
            return;
        Iterator<Map.Entry<String, Boolean>> groupIterator = groupInfoEntity.getJfmsGroupMemberMap().entrySet().iterator();
        while (groupIterator.hasNext()){
            Map.Entry<String, Boolean> next = groupIterator.next();
            if (!next.getKey().equals(from))
                p2PChatManager.sendOnlineOrOffline(next.getKey(), message);
        }
    }

}
