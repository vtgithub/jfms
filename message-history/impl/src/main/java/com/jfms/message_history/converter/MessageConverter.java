package com.jfms.message_history.converter;

import com.jfms.message_history.dal.EntityStatus;
import com.jfms.message_history.dal.entity.GroupEntity;
import com.jfms.message_history.dal.entity.P2PEntity;
import com.jfms.message_history.model.HistoryMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageConverter {

    public P2PEntity historyMessageToP2pEntity(String userId, HistoryMessage messageForHistory) {
        if (messageForHistory == null)
            return null;
        P2PEntity p2PEntity = new P2PEntity();
        p2PEntity.setOwner(userId);
        p2PEntity.setBody(messageForHistory.getBody());
        p2PEntity.setSender(messageForHistory.getSender());
        p2PEntity.setMessageId(messageForHistory.getMessageId());
        p2PEntity.setSubject(messageForHistory.getSubject());
        p2PEntity.setTime(messageForHistory.getTime());
        p2PEntity.setStatus(EntityStatus.INSERTED.getValue());
        return p2PEntity;
    }

    public void historyMessageToUpdatedP2pEntity(HistoryMessage messageForHistory, P2PEntity previousP2PEntity) {
        previousP2PEntity.setBody(messageForHistory.getBody());
//        previousP2PEntity.setFrom(messageForHistory.getFrom());
//        previousP2PEntity.setMessageId(messageForHistory.getMessageId());
        previousP2PEntity.setSubject(messageForHistory.getSubject());
//        previousP2PEntity.setTime(messageForHistory.getTime());
        previousP2PEntity.setStatus(EntityStatus.UPDATED.getValue());
    }

    public List<HistoryMessage> p2PEntityListToHistoryMessageList(List<P2PEntity> p2PEntityList) {
        if (p2PEntityList == null)
            return null;
        List<HistoryMessage> historyMessageList = new ArrayList<HistoryMessage>();
        for (P2PEntity p2PEntity : p2PEntityList) {
            historyMessageList.add(p2PEntityToHistoyMessage(p2PEntity));
        }
        return historyMessageList;
    }

    private HistoryMessage p2PEntityToHistoyMessage(P2PEntity p2PEntity) {
        if (p2PEntity == null)
            return null;
        HistoryMessage historyMessage = new HistoryMessage(
                p2PEntity.getMessageId(),
                p2PEntity.getSender(),
                p2PEntity.getBody(),
                p2PEntity.getSubject(),
                p2PEntity.getTime()

        );
        return historyMessage;
    }

    public GroupEntity historyMessageToGroupEntity(String groupId, HistoryMessage messageForHistory) {
        if (messageForHistory == null)
            return null;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupId(groupId);
        groupEntity.setBody(messageForHistory.getBody());
        groupEntity.setSender(messageForHistory.getSender());
        groupEntity.setMessageId(messageForHistory.getMessageId());
        groupEntity.setSubject(messageForHistory.getSubject());
        groupEntity.setTime(messageForHistory.getTime());
        groupEntity.setStatus(EntityStatus.INSERTED.getValue());
        return groupEntity;
    }

    public void historyMessageToUpdatedGroupEntity(HistoryMessage messageForUpdate, GroupEntity previousGroupEntity) {
        previousGroupEntity.setBody(messageForUpdate.getBody());
        previousGroupEntity.setSubject(messageForUpdate.getSubject());
        previousGroupEntity.setStatus(EntityStatus.UPDATED.getValue());
    }

    public List<HistoryMessage> groupEntityListToHistoryMessageList(List<GroupEntity> groupEntityList) {
        if (groupEntityList == null)
            return null;
        List<HistoryMessage> historyMessageList = new ArrayList<HistoryMessage>();
        for (GroupEntity groupEntity : groupEntityList) {
            historyMessageList.add(groupEntityToHistoyMessage(groupEntity));
        }
        return historyMessageList;
    }

    private HistoryMessage groupEntityToHistoyMessage(GroupEntity groupEntity) {
        return new HistoryMessage(
                groupEntity.getMessageId(),
                groupEntity.getSender(),
                groupEntity.getBody(),
                groupEntity.getSubject(),
                groupEntity.getTime()
        );
    }
}
