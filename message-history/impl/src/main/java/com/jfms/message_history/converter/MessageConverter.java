package com.jfms.message_history.converter;

import com.jfms.message_history.dal.EntityStatus;
import com.jfms.message_history.dal.entity.P2PEntity;
import com.jfms.message_history.model.P2PMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageConverter {

    public P2PEntity p2PMessageToP2pEntity(String userId, P2PMessage messageForHistory) {
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

    public void p2PMessageToP2pEntity(P2PMessage messageForHistory, P2PEntity previousP2PEntity) {
        previousP2PEntity.setBody(messageForHistory.getBody());
//        previousP2PEntity.setFrom(messageForHistory.getFrom());
//        previousP2PEntity.setMessageId(messageForHistory.getMessageId());
        previousP2PEntity.setSubject(messageForHistory.getSubject());
//        previousP2PEntity.setTime(messageForHistory.getTime());
        previousP2PEntity.setStatus(EntityStatus.UPDATED.getValue());
    }

    public List<P2PMessage> p2PEntityListToP2PMessageList(List<P2PEntity> p2PEntityList) {
        if (p2PEntityList == null)
            return null;
        List<P2PMessage> p2PMessageList = new ArrayList<P2PMessage>();
        for (P2PEntity p2PEntity : p2PEntityList) {
            p2PMessageList.add(p2PEntityToP2PMessage(p2PEntity));
        }
        return p2PMessageList;
    }

    private P2PMessage p2PEntityToP2PMessage(P2PEntity p2PEntity) {
        if (p2PEntity == null)
            return null;
        P2PMessage p2PMessage = new P2PMessage(
                p2PEntity.getMessageId(),
                p2PEntity.getSender(),
                p2PEntity.getBody(),
                p2PEntity.getSubject(),
                p2PEntity.getTime()

        );
        return p2PMessage;
    }
}
