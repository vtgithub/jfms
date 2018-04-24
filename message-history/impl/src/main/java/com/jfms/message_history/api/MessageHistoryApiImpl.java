package com.jfms.message_history.api;

import com.jfms.message_history.MessageHistoryApi;
import com.jfms.message_history.model.P2PMessage;
import com.jfms.message_history.model.P2PMessageRequest;
import com.jfms.message_history.service.P2PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MessageHistoryApiImpl implements MessageHistoryApi{

    @Autowired
    private P2PService p2PService;

    public void saveHistoryMessage(@PathVariable("userId") String userId, @RequestBody P2PMessage messageForHistory) {
        p2PService.saveMessage(userId, messageForHistory);

    }

    public void UpdateHistoryMessage(@PathVariable("userId") String userId, @RequestBody P2PMessage messageForUpdate) {
        p2PService.editMessage(userId, messageForUpdate);
    }

    public @ResponseBody List<P2PMessage> getUserP2PMessages(@PathVariable("userId") String userId,
                                                             @PathVariable("rosterId") String rosterId,
                                                             @RequestBody P2PMessageRequest p2PMessageRequest) {
        List<P2PMessage> p2PMessageList = p2PService.getUserP2PMessages(userId, rosterId, p2PMessageRequest);
        return p2PMessageList;
    }

    public void deleteP2PMessage(@PathVariable("userId") String userId, @RequestBody List<String> messageIdList) {
        p2PService.deleteP2PMessage(userId, messageIdList);
    }

}
