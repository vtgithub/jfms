package com.jfms.message_history.api;

import com.jfms.message_history.P2PHistoryApi;
import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.model.HistoryMessageRequest;
import com.jfms.message_history.service.P2PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class P2PHistoryApiImpl implements P2PHistoryApi {

    @Autowired
    private P2PService p2PService;

    public @ResponseBody void saveHistoryMessage(
            @PathVariable("userId") String userId, @RequestBody HistoryMessage messageForHistory) {
        p2PService.saveMessage(userId, messageForHistory);

    }

    public @ResponseBody void UpdateHistoryMessage(
            @PathVariable("userId") String userId, @RequestBody HistoryMessage messageForUpdate) {
        p2PService.editMessage(userId, messageForUpdate);
    }

    public @ResponseBody List<HistoryMessage> getUserP2PMessages(@PathVariable("userId") String userId,
                                                                 @PathVariable("rosterId") String rosterId,
                                                                 @RequestParam("pageSize") Integer pageSize,
                                                                 @RequestParam("pageNumber") Integer pageNumber) {
        List<HistoryMessage> historyMessageList = p2PService.getUserP2PMessages(
                userId, rosterId, pageSize, pageNumber);
        return historyMessageList;
    }

    public @ResponseBody void deleteP2PMessage(
            @PathVariable("userId") String userId, @RequestBody List<String> messageIdList) {
        p2PService.deleteP2PMessage(userId, messageIdList);
    }

}
