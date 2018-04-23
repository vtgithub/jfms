package com.jfms.message_history.dal;

import com.jfms.message_history.dal.entity.P2PEntity;
import com.jfms.message_history.dal.entity.P2PUpdateEntity;
import org.springframework.stereotype.Component;

@Component
public class DalAssistant {
    public P2PUpdateEntity getP2PUpdateEntityFromP2PEntity(String previousValue, P2PEntity p2PEntity){
        if (p2PEntity == null)
            return null;
        P2PUpdateEntity p2PUpdateEntity = new P2PUpdateEntity();
        p2PUpdateEntity.setMessageId(p2PEntity.getMessageId());
        p2PUpdateEntity.setEditor(p2PEntity.getFrom());
        p2PUpdateEntity.setPreviousValue(previousValue);
        p2PUpdateEntity.setCurrentValue(p2PEntity.getBody());
        return p2PUpdateEntity;
    }
}
