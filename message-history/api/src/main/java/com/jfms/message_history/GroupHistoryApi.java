package com.jfms.message_history;

import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.model.HistoryMessageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/history/group", produces = "application/json", consumes = "application/json")
public interface GroupHistoryApi {

    @RequestMapping(value = "/{groupId}", method = RequestMethod.POST)
    void saveGroupHistoryMessage(@PathVariable("groupId") String groupId, @RequestBody HistoryMessage messageForHistory);

    @RequestMapping(value = "/{groupId}", method = RequestMethod.PUT)
    void updateGroupHistoryMessage(@PathVariable("groupId") String groupId, @RequestBody HistoryMessage messageForUpdate);

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET) // getting messages by pagination
    List<HistoryMessage> getGroupMessages(@PathVariable("groupId") String userId,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNumber") Integer pageNumber);

    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    void deleteGroupMessage(@PathVariable("groupId") String groupId, @RequestBody List<String> messageIdList);

}
