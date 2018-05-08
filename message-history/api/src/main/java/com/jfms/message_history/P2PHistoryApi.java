package com.jfms.message_history;

import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.model.HistoryMessageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/history/p2p", produces = "application/json", consumes = "application/json")
public interface P2PHistoryApi {

    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    void saveP2PHistoryMessage(@PathVariable("userId") String userId, @RequestBody HistoryMessage messageForHistory);

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    void updateP2PHistoryMessage(@PathVariable("userId") String userId, @RequestBody HistoryMessage messageForUpdate);

    @RequestMapping(value = "/{userId}/{rosterId}", method = RequestMethod.GET) // getting messages by pagination
    List<HistoryMessage> getUserP2PMessages(@PathVariable("userId") String userId,
                                            @PathVariable("rosterId") String rosterId,
                                            @RequestParam("pageSize") Integer pageSize,
                                            @RequestParam("pageNumber") Integer pageNumber);

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    void deleteP2PMessage(@PathVariable("userId") String userId, @RequestBody List<String> messageIdList);


}