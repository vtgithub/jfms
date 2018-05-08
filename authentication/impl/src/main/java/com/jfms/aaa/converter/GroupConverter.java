package com.jfms.aaa.converter;

import com.jfms.aaa.dal.EntityStatus;
import com.jfms.aaa.dal.entity.GroupEntity;
import com.jfms.aaa.dal.entity.GroupMemberObject;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.aaa.model.GroupMember;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupConverter {

    public GroupEntity getEntity(Integer status, GroupInfo groupInfo) {
        return new GroupEntity(
                status,
                groupInfo.getDisplayName(),
                groupInfo.getOwner(),
                getMemberObjectList(groupInfo.getMemberList()),
                groupInfo.getType()
        );
    }

    public GroupMemberObject getMemberObject(GroupMember groupMember){
        return new GroupMemberObject(groupMember.getUserName(), groupMember.getAdmin());
    }

    public List<GroupMemberObject> getMemberObjectList(List<GroupMember> groupMemberList){
        if (groupMemberList == null || groupMemberList.size() == 0)
            return null;
        List<GroupMemberObject> groupMemberObjectList = new ArrayList<GroupMemberObject>();
        for (GroupMember groupMember : groupMemberList) {
            if (groupMember != null)
                groupMemberObjectList.add(getMemberObject(groupMember));
        }
        return groupMemberObjectList;
    }


    public GroupInfo getInfo(GroupEntity groupEntity) {
        return new GroupInfo(
                groupEntity.getId().toString(),
                groupEntity.getDisplayName(),
                groupEntity.getOwner(),
                getMemberList(groupEntity.getMemberObjectList()),
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
        groupEntity.setMemberObjectList(getMemberObjectList(groupInfo.getMemberList()));

    }

    // ------------------------------------------------

    public GroupMember getMember(GroupMemberObject groupMemberObject){
        return new GroupMember(groupMemberObject.getUserName(), groupMemberObject.getAdmin());
    }
    public List<GroupMember> getMemberList(List<GroupMemberObject> groupMemberObjectList){
        if (groupMemberObjectList == null || groupMemberObjectList.size() == 0)
            return null;
        List<GroupMember> groupMemberList =  new ArrayList<>();
        for (GroupMemberObject groupMemberObject : groupMemberObjectList) {
            groupMemberList.add(getMember(groupMemberObject));
        }
        return groupMemberList;
    }

}
