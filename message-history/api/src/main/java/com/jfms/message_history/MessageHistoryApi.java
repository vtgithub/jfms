package com.jfms.message_history;

import com.jfms.message_history.model.P2PMessage;
import com.jfms.message_history.model.P2PMessageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping(value = "/history", produces = "application/json", consumes = "application/json")
public interface MessageHistoryApi {
    @RequestMapping(value = "/p2p/{userId}", method = RequestMethod.POST)
    void saveHistoryMessage(@PathVariable("userId") String userId, @RequestBody P2PMessage messageForHistory);
    @RequestMapping(value = "/p2p/{userId}", method = RequestMethod.PUT)
    void UpdateHistoryMessage(@PathVariable("userId") String userId, @RequestBody P2PMessage messageForUpdate);
    @RequestMapping(value = "/p2p/{userId}", method = RequestMethod.POST) // getting messages by pagination
    List<P2PMessage> getUserP2PMessages(@PathVariable("userId") String userId, @RequestBody P2PMessageRequest p2PMessageRequest);
    @RequestMapping(value = "/p2p/{userId}", method = RequestMethod.DELETE)
    void deleteP2PMessage(@PathVariable("userId") String userId, @RequestBody List<String> messageIdList);
}