package com.jfms.message_history;

import com.jfms.message_history.model.HistoryMessageRequest;
import com.jfms.message_history.model.P2PMessage;
import com.jfms.message_history.model.P2PMessageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping(value = "/history", produces = "application/json", consumes = "application/json")
public interface MessageHistoryApi {
    @RequestMapping(value = "/p2p", method = RequestMethod.POST)
    void saveHistoryMessage(@RequestBody HistoryMessageRequest historyMessageRequest);
    @RequestMapping(value = "/p2p", method = RequestMethod.PUT)
    void UpdateHistoryMessage(@RequestBody HistoryMessageRequest historyMessageRequest);
    @RequestMapping(value = "/p2p/{username}", method = RequestMethod.POST)
    List<P2PMessage> getUserP2PMessage(@RequestBody P2PMessageRequest p2PMessageRequest);
}
