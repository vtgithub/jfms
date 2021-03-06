package com.jfms.message_history.dal;

import com.jfms.message_history.dal.entity.*;
import org.springframework.stereotype.Component;

@Component
public class DalAssistant {
    public P2PUpdateEntity getP2PUpdateEntityFromP2PEntity(String previousValue, Long time, P2PEntity p2PEntity){
        if (p2PEntity == null)
            return null;
        P2PUpdateEntity p2PUpdateEntity = new P2PUpdateEntity();
        p2PUpdateEntity.setMessageId(p2PEntity.getMessageId());
        p2PUpdateEntity.setEditor(p2PEntity.getSender());
        p2PUpdateEntity.setPreviousValue(previousValue);
        p2PUpdateEntity.setCurrentValue(p2PEntity.getBody());
        p2PUpdateEntity.setUpdateTime(time);
        return p2PUpdateEntity;
    }

    public GroupUpdateEntity getGroupUpdateEntityFromGroupEntity(
            String previousValue, Long time, GroupEntity groupEntity) {
        if (groupEntity == null)
            return null;
        GroupUpdateEntity groupUpdateEntity = new GroupUpdateEntity();
        groupUpdateEntity.setMessageId(groupEntity.getMessageId());
        groupUpdateEntity.setEditor(groupEntity.getSender());
        groupUpdateEntity.setPreviousValue(previousValue);
        groupUpdateEntity.setCurrentValue(groupEntity.getBody());
        groupUpdateEntity.setUpdateTime(time);
        return groupUpdateEntity;
    }

    public ChannelUpdateEntity getChannelUpdateEntityFromChannelEntity(
            String previousValue, Long time, ChannelEntity channelEntity) {
        if (channelEntity == null)
            return null;
        ChannelUpdateEntity channelUpdateEntity = new ChannelUpdateEntity();
        channelUpdateEntity.setMessageId(channelEntity.getMessageId());
        channelUpdateEntity.setEditor(channelEntity.getSender());
        channelUpdateEntity.setPreviousValue(previousValue);
        channelUpdateEntity.setCurrentValue(channelEntity.getBody());
        channelUpdateEntity.setUpdateTime(time);
        return channelUpdateEntity;
    }
}
