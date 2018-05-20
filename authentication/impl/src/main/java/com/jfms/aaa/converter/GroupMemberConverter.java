package com.jfms.aaa.converter;

import com.jfms.aaa.dal.entity.GroupMemberObject;
import com.jfms.aaa.model.GroupMember;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMemberConverter {


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
