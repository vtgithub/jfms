package com.jfms.aaa;

import com.jfms.aaa.model.GroupInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(path = "/aaa/channel" , produces = "application/json", consumes = "application/json")
public interface ChannelApi {
    @RequestMapping(method = RequestMethod.POST)
    String addChannel(@RequestBody GroupInfo channelInfo);

    @RequestMapping(method = RequestMethod.PUT)
    void editChannel(@RequestBody GroupInfo channelInfo) ;

    @RequestMapping(path = "/{cId}", method = RequestMethod.GET)
    GroupInfo getChannel(@PathVariable("cId") String channelId);

    @RequestMapping(path = "/{cId}", method = RequestMethod.DELETE)
    void deleteChannel(@PathVariable("cId") String channelId);
}
