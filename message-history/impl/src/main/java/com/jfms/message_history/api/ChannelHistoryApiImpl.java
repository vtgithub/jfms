package com.jfms.message_history.api;

import com.jfms.message_history.ChannelHistoryApi;
import com.jfms.message_history.GroupHistoryApi;
import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.service.ChannelService;
import com.jfms.message_history.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChannelHistoryApiImpl implements ChannelHistoryApi {

    @Autowired
    private ChannelService channelService;

    public @ResponseBody void saveChannelHistoryMessage(
            @PathVariable("channelId") String channelId, @RequestBody HistoryMessage messageForHistory) {
        channelService.saveMessage(channelId, messageForHistory);
        System.out.println("channel message has been saved successfully");
    }

    public @ResponseBody void updateChannelHistoryMessage(
            @PathVariable("channelId") String channelId, @RequestBody HistoryMessage messageForUpdate) {
        channelService.editMessage(channelId, messageForUpdate);
        System.out.println("channel message has been updated successfully");
    }

    public @ResponseBody List<HistoryMessage> getChannelMessages(
            @PathVariable("channelId") String channelId,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNumber") Integer pageNumber) {
        List<HistoryMessage> historyMessageList = channelService.getChannelMessages(channelId, pageSize, pageNumber);
        System.out.println("channel message has been fetched successfully");
        return historyMessageList;
    }

    public @ResponseBody void deleteChannelMessage(
            @PathVariable("channelId") String channelId, @RequestBody List<String> messageIdList) {
        channelService.deleteMessages(channelId, messageIdList);
        System.out.println("channel message has been deleted successfully");
    }
}
