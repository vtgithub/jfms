package com.jfms.aaa.converter;

import com.jfms.aaa.dal.EntityStatus;
import com.jfms.aaa.dal.entity.GroupEntity;
import com.jfms.aaa.dal.entity.GroupMemberObject;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.aaa.model.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupConverter {

    @Autowired
    private GroupMemberConverter groupMemberConverter;

    public GroupEntity getEntity(Integer status, GroupInfo groupInfo) {
        return new GroupEntity(
                status,
                groupInfo.getDisplayName(),
                groupInfo.getOwner(),
                groupMemberConverter.getMemberObjectList(groupInfo.getMemberList()),
                groupInfo.getType()
        );
    }

    public GroupInfo getInfo(GroupEntity groupEntity) {
        return new GroupInfo(
                groupEntity.getId().toString(),
                groupEntity.getDisplayName(),
                groupEntity.getOwner(),
                groupMemberConverter.getMemberList(groupEntity.getMemberObjectList()),
                groupEntity.getType()
        );
    }


    public void updateEntityByInfo(GroupEntity groupEntity, GroupInfo groupInfo) {
        List<String> displayNameChangeHistory = groupEntity.getDisplayNameChangeHistory();
        if (displayNameChangeHistory == null || displayNameChangeHistory.size() == 0)
            displayNameChangeHistory = new ArrayList<>();
        groupEntity.setDisplayNameChangeHistory(displayNameChangeHistory);
        displayNameChangeHistory.add(groupEntity.getDisplayName());
        groupEntity.setDisplayName(groupInfo.getDisplayName());
        groupEntity.setOwner(groupInfo.getOwner());
        groupEntity.setType(groupInfo.getType());
        groupEntity.setMemberObjectList(
                groupMemberConverter.getMemberObjectList(groupInfo.getMemberList())
        );

    }

}
