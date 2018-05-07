package com.jfms.message_history.api;

import com.jfms.message_history.GroupHistoryApi;
import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.model.HistoryMessageRequest;
import com.jfms.message_history.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GroupHistoryApiImpl implements GroupHistoryApi{

    @Autowired
    private GroupService groupService;

    public @ResponseBody void saveHistoryMessage(
            @PathVariable("groupId") String groupId, @RequestBody HistoryMessage messageForHistory) {
        groupService.saveMessage(groupId, messageForHistory);
    }

    public @ResponseBody void UpdateHistoryMessage(
            @PathVariable("groupId") String groupId, @RequestBody HistoryMessage messageForUpdate) {
        groupService.editMessage(groupId, messageForUpdate);
    }

    public @ResponseBody List<HistoryMessage> getUserP2PMessages(
            @PathVariable("groupId") String groupId,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNumber") Integer pageNumber) {
        List<HistoryMessage> historyMessageList = groupService.getGroupMessages(groupId, pageSize, pageNumber);
        return historyMessageList;

    }

    public @ResponseBody void deleteP2PMessage(
            @PathVariable("groupId") String groupId, @RequestBody List<String> messageIdList) {
        groupService.deleteMessages(groupId, messageIdList);
    }
}
