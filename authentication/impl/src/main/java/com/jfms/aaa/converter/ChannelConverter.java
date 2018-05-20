package com.jfms.aaa.converter;

import com.jfms.aaa.dal.entity.ChannelEntity;
import com.jfms.aaa.model.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChannelConverter {

    @Autowired
    private GroupMemberConverter groupMemberConverter;

    public ChannelEntity getEntity(Integer status, GroupInfo channelInfo) {
        return new ChannelEntity(
                status,
                channelInfo.getDisplayName(),
                channelInfo.getOwner(),
                groupMemberConverter.getMemberObjectList(channelInfo.getMemberList()),
                channelInfo.getType()
        );
    }

    public GroupInfo getInfo(ChannelEntity channelEntity) {
        return new GroupInfo(
                channelEntity.getId().toString(),
                channelEntity.getDisplayName(),
                channelEntity.getOwner(),
                groupMemberConverter.getMemberList(channelEntity.getMemberObjectList()),
                channelEntity.getType()
        );
    }


    public void updateEntityByInfo(ChannelEntity channelEntity, GroupInfo channelInfo) {
        List<String> displayNameChangeHistory = channelEntity.getDisplayNameChangeHistory();
        if (displayNameChangeHistory == null || displayNameChangeHistory.size() == 0)
            displayNameChangeHistory = new ArrayList<>();
        channelEntity.setDisplayNameChangeHistory(displayNameChangeHistory);
        displayNameChangeHistory.add(channelEntity.getDisplayName());
        channelEntity.setDisplayName(channelInfo.getDisplayName());
        channelEntity.setOwner(channelInfo.getOwner());
        channelEntity.setType(channelInfo.getType());
        channelEntity.setMemberObjectList(
                groupMemberConverter.getMemberObjectList(channelInfo.getMemberList())
        );

    }

}
