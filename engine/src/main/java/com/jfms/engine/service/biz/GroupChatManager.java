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
import java.util.List;
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
    ChannelApiClient channelApiClient;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ChannelRepository channelRepository;
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

    public void createGroup(
            JFMSClientGroupCreationMessage jfmsClientGroupCreationMessage, WebSocketSession session, boolean isChannel){
        GroupInfo groupInfo = GroupApiClient.getGroupInfo(
                jfmsClientGroupCreationMessage.getDisplayName(),
                jfmsClientGroupCreationMessage.getCreator(),
                jfmsClientGroupCreationMessage.getJfmsGroupMemberMap(),
                jfmsClientGroupCreationMessage.getType()
        );
        String id = null;
        GroupInfoEntity groupInfoEntity = groupConverter.getEntityFromJFMSMessage(jfmsClientGroupCreationMessage);
        if (isChannel == true) {
            id = channelApiClient.addChannel(groupInfo);
            channelRepository.saveChannelInfo(id, groupInfoEntity);
        } else {
            id = groupApiClient.addGroup(groupInfo);
            groupRepository.saveGroupInfo(id, groupInfoEntity);
        }
        sendGroupOrChannelCreationOrInfoUpdateMessageToMembers(
                jfmsMessageConverter.clientGroupCreationToServerGroupCreation(id, jfmsClientGroupCreationMessage)
        );
        //todo group message History
        try {
            String message = (isChannel == true) ? "{\"groupId\":\"" + id + "\"}" : "{\"channelId\":\"" + id + "\"}";
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
    }

    public void editGroupInfo(JFMSClientGroupInfoEditMessage jfmsClientGroupInfoEditMessage, boolean isChannel){
        GroupInfo groupInfo = GroupApiClient.getGroupInfo(
                jfmsClientGroupInfoEditMessage.getId(),
                jfmsClientGroupInfoEditMessage.getDisplayName(),
                jfmsClientGroupInfoEditMessage.getCreator(),
                jfmsClientGroupInfoEditMessage.getJfmsGroupMemberMap(),
                jfmsClientGroupInfoEditMessage.getType()
        );
        GroupInfoEntity groupInfoEntity = groupConverter.getEntityFromJFMSMessage(jfmsClientGroupInfoEditMessage);

        if(isChannel == true){
            channelApiClient.editChannel(groupInfo);
            channelRepository.saveChannelInfo(groupInfo.getId(), groupInfoEntity);
        }else{
            groupApiClient.editGroup(groupInfo);
            groupRepository.saveGroupInfo(groupInfo.getId(), groupInfoEntity);
        }

        sendGroupOrChannelCreationOrInfoUpdateMessageToMembers(
                jfmsMessageConverter.clientGroupInfoEditToServerGroupCreation(jfmsClientGroupInfoEditMessage));
        //todo group message History
    }


    public void sendGroupMessage(
            JFMSClientSendMessage jfmsClientGroupSendMessage, WebSocketSession session, boolean isChannel) {
        String messageId = UUID.randomUUID().toString();
        JFMSServerGroupSendMessage jfmsServerGroupSendMessage =
                jfmsMessageConverter.clientSendToServerGroupSend(messageId, jfmsClientGroupSendMessage);
        sendGroupOrChannelOnlineOrOffline(
                jfmsClientGroupSendMessage.getFrom(),
                jfmsClientGroupSendMessage.getTo(),
                gson.toJson(jfmsServerGroupSendMessage),
                isChannel
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
        historySaveGroupOrChannel(jfmsClientGroupSendMessage.getTo(), groupHistoryMessage, isChannel);
    }


    public void editGroupMessage(JFMSClientEditMessage jfmsClientGroupEditMessage, boolean isChannel){
        JFMSServerGroupEditMessage jfmsServerGroupEditMessage=
                jfmsMessageConverter.clientEditToServerGroupEdit(jfmsClientGroupEditMessage);
        sendGroupOrChannelOnlineOrOffline(
                jfmsClientGroupEditMessage.getFrom(),
                jfmsClientGroupEditMessage.getTo(),
                gson.toJson(jfmsServerGroupEditMessage),
                isChannel
        );
        HistoryMessage groupHistoryMessage = MessageHistoryGroupApiClient.getGroupHistoryMessage(
                jfmsClientGroupEditMessage.getId(),
                jfmsClientGroupEditMessage.getFrom(),
                jfmsClientGroupEditMessage.getBody(),
                jfmsClientGroupEditMessage.getSubject(),
                jfmsClientGroupEditMessage.getEditTime()
        );
        historyEditGroupOrChannel(jfmsClientGroupEditMessage.getTo(), groupHistoryMessage, isChannel);
    }


    public void deleteGroupMessage(JFMSClientDeleteMessage jfmsClientGroupDeleteMessage, boolean isChannel){
        JFMSServerGroupDeleteMessage jfmsServerGroupDeleteMessage=
                jfmsMessageConverter.clientDeleteToServerGroupDelete(jfmsClientGroupDeleteMessage);
        sendGroupOrChannelOnlineOrOffline(
                jfmsClientGroupDeleteMessage.getFrom(),
                jfmsClientGroupDeleteMessage.getTo(),
                gson.toJson(jfmsServerGroupDeleteMessage),
                isChannel
        );
        historyDeleteGroupOrChannel(jfmsClientGroupDeleteMessage.getTo(), jfmsClientGroupDeleteMessage.getIdList(), isChannel);

    }

    public void groupIsTypingMessage(JFMSClientIsTypingMessage jfmsClientGroupIsTypingMessage, boolean isChannel) {
        JFMSServerGroupIsTypingMessage jfmsServerGroupIsTypingMessage =
                jfmsMessageConverter.clientIsTypingToServerGroupIsTyping(jfmsClientGroupIsTypingMessage);
        sendGroupOnline(
                jfmsClientGroupIsTypingMessage.getFrom(),
                jfmsClientGroupIsTypingMessage.getTo(),
                gson.toJson(jfmsServerGroupIsTypingMessage),
                isChannel
        );
    }

    public void setGroupLeaveTime(
            JFMSClientConversationLeaveMessage jfmsClientGroupConversationLeaveMessage, boolean isChannel) {
        lastSeenRepository.setLastSeen(
                jfmsClientGroupConversationLeaveMessage.getFrom(),
                jfmsClientGroupConversationLeaveMessage.getTo(),
                jfmsClientGroupConversationLeaveMessage.getLeaveTime()
        );
        JFMSServerGroupConversationMessage jfmsServerGroupConversationMessage =
                jfmsMessageConverter.clientConversationLeaveToServerGroupConversation(jfmsClientGroupConversationLeaveMessage);
        sendGroupOrChannelOnlineOrOffline(
                jfmsClientGroupConversationLeaveMessage.getFrom(),
                jfmsClientGroupConversationLeaveMessage.getTo(),
                gson.toJson(jfmsServerGroupConversationMessage),
                isChannel
        );

    }

    //-----------------------------

    private void sendGroupOrChannelCreationOrInfoUpdateMessageToMembers(
            JFMSServerGroupCreationMessage jfmsServerGroupCreationMessage) {
        Iterator<Map.Entry<String, Boolean>> memberIterator = jfmsServerGroupCreationMessage.getJfmsGroupMemberMap().entrySet().iterator();
        while (memberIterator.hasNext()){
            Map.Entry<String, Boolean> member = memberIterator.next();
            p2PChatManager.sendOnlineOrOffline(member.getKey(), gson.toJson(jfmsServerGroupCreationMessage));

        }
    }

    private void sendGroupOnline(String from, String groupId, String message, boolean isChannel) {
        GroupInfoEntity groupInfoEntity = (isChannel)?channelRepository.getChannelInfo(groupId):groupRepository.getGroupInfo(groupId);
        if (!isChannel && !groupInfoEntity.contains(from))
            return;
        if (isChannel && !groupInfoEntity.containsAsAdmin(from))
            return;
        Iterator<Map.Entry<String, Boolean>> gIterator = groupInfoEntity.getJfmsGroupMemberMap().entrySet().iterator();
        while (gIterator.hasNext()){
            Map.Entry<String, Boolean> next = gIterator.next();
            if (!next.getKey().equals(from))
                p2PChatManager.sendOnline(next.getKey(), message);
        }
    }

    private void sendGroupOrChannelOnlineOrOffline(String from, String groupId, String message, boolean isChannel) {
        GroupInfoEntity groupInfoEntity = (isChannel)?channelRepository.getChannelInfo(groupId):groupRepository.getGroupInfo(groupId);
        if (!isChannel && !groupInfoEntity.getJfmsGroupMemberMap().containsKey(from))
            return;
        if (isChannel && !groupInfoEntity.containsAsAdmin(from))
            return;
        Iterator<Map.Entry<String, Boolean>> groupIterator = groupInfoEntity.getJfmsGroupMemberMap().entrySet().iterator();
        while (groupIterator.hasNext()){
            Map.Entry<String, Boolean> next = groupIterator.next();
            if (!next.getKey().equals(from))
                p2PChatManager.sendOnlineOrOffline(next.getKey(), message);
        }
    }

    //--------------- select for history
    private void historySaveGroupOrChannel(String owner, HistoryMessage groupHistoryMessage, boolean isChannel) {
        if (isChannel == true){
            messageHistoryApiFactory.getChannelApi().saveChannelHistoryMessage(owner, groupHistoryMessage);
        }else {
            messageHistoryApiFactory.getGroupApi().saveGroupHistoryMessage(owner, groupHistoryMessage);
        }
    }

    private void historyEditGroupOrChannel(String owner, HistoryMessage groupHistoryMessage, boolean isChannel) {
        if (isChannel == true)
            messageHistoryApiFactory.getChannelApi().updateChannelHistoryMessage(owner, groupHistoryMessage);
        else
            messageHistoryApiFactory.getGroupApi().updateGroupHistoryMessage(owner, groupHistoryMessage);
    }

    private void historyDeleteGroupOrChannel(String owner, List<String> messageIdList, boolean isChannel) {
        if (isChannel == true)
            messageHistoryApiFactory.getChannelApi().deleteChannelMessage(owner, messageIdList);
        else
            messageHistoryApiFactory.getGroupApi().deleteGroupMessage(owner, messageIdList);
    }

}
