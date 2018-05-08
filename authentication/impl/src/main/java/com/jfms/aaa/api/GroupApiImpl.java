package com.jfms.aaa.api;

import com.jfms.aaa.GroupApi;
import com.jfms.aaa.dal.repository.NotFoundException;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.aaa.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class GroupApiImpl implements GroupApi{

    @Autowired
    private GroupService groupService;

    public @ResponseBody String addGroup(@RequestBody GroupInfo groupInfo) {
        String groupId = groupService.addGroup(groupInfo);
        return groupId;
    }


    public @ResponseBody void editGroup(GroupInfo groupInfo){
        try {
            groupService.editGroup(groupInfo);
        } catch (NotFoundException e) {
            e.printStackTrace();
            //todo log
            throw new InvalidInputDataException(e);
        }
    }

    public @ResponseBody GroupInfo getGroup(@PathVariable("gId")  String groupId) {
        GroupInfo groupInfo = null;
        try {
            groupInfo = groupService.getGroupInfo(groupId);
        } catch (NotFoundException e) {
            e.printStackTrace();
            //todo log
            throw new InvalidInputDataException(e);
        }
        return groupInfo;
    }

    public @ResponseBody void deleteGroup(@PathVariable("gId") String groupId) {
        groupService.deleteGroup(groupId);
    }
}
