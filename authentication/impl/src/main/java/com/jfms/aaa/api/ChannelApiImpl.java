package com.jfms.aaa.api;

import com.jfms.aaa.ChannelApi;
import com.jfms.aaa.dal.repository.NotFoundException;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.aaa.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChannelApiImpl implements ChannelApi{

    @Autowired
    private ChannelService channelService;

    public @ResponseBody String addChannel(@RequestBody GroupInfo channelInfo) {
        String channelId = channelService.addChannel(channelInfo);
        return channelId;
    }


    public @ResponseBody void editChannel(@RequestBody GroupInfo channelInfo){
        try {
            channelService.editChannel(channelInfo);
        } catch (NotFoundException e) {
            e.printStackTrace();
            //todo log
            throw new InvalidInputDataException(e);
        }
    }

    public @ResponseBody GroupInfo getChannel(@PathVariable("cId")  String channelId) {
        GroupInfo channelInfo = null;
        try {
            channelInfo = channelService.getChannelInfo(channelId);
        } catch (NotFoundException e) {
            e.printStackTrace();
            //todo log
            throw new InvalidInputDataException(e);
        }
        return channelInfo;
    }

    public @ResponseBody void deleteChannel(@PathVariable("cId") String channelId) {
        try {
            channelService.deleteChannel(channelId);
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new InvalidInputDataException(e);
        }
    }
}
