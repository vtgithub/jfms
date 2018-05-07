package com.jfms.message_history;

import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.model.HistoryMessageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/history", produces = "application/json", consumes = "application/json")
public interface GroupHistoryApi {

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.POST)
    void saveHistoryMessage(@PathVariable("groupId") String groupId, @RequestBody HistoryMessage messageForHistory);

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.PUT)
    void UpdateHistoryMessage(@PathVariable("groupId") String groupId, @RequestBody HistoryMessage messageForUpdate);

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET) // getting messages by pagination
    List<HistoryMessage> getUserP2PMessages(@PathVariable("groupId") String userId,
                                            @RequestParam("pageSize") Integer pageSize,
                                            @RequestParam("pageNumber") Integer pageNumber);

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.DELETE)
    void deleteP2PMessage(@PathVariable("groupId") String groupId, @RequestBody List<String> messageIdList);

}
