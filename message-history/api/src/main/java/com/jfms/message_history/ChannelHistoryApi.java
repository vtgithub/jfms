package com.jfms.message_history;

import com.jfms.message_history.model.HistoryMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/history/channel", produces = "application/json", consumes = "application/json")
public interface ChannelHistoryApi {

    @RequestMapping(value = "/{channelId}", method = RequestMethod.POST)
    void saveChannelHistoryMessage(@PathVariable("channelId") String channelId, @RequestBody HistoryMessage messageForHistory);

    @RequestMapping(value = "/{channelId}", method = RequestMethod.PUT)
    void updateChannelHistoryMessage(@PathVariable("channelId") String channelId, @RequestBody HistoryMessage messageForUpdate);

    @RequestMapping(value = "/{channelId}", method = RequestMethod.GET) // getting messages by pagination
    List<HistoryMessage> getChannelMessages(@PathVariable("channelId") String userId,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNumber") Integer pageNumber);

    @RequestMapping(value = "/{channelId}", method = RequestMethod.DELETE)
    void deleteChannelMessage(@PathVariable("channelId") String channelId, @RequestBody List<String> messageIdList);

}
