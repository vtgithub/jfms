package com.jfms.aaa.api;

import com.jfms.aaa.GroupApi;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.aaa.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupApiImpl implements GroupApi{

    @Autowired
    private GroupService groupService;

    public String addGroup(GroupInfo groupInfo) {
        String groupId = groupService.addGroup(groupInfo);
        return null;
    }

    public GroupInfo getGroup(String groupId) {
        return null;
    }
}
