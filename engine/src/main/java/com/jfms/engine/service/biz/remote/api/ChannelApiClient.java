package com.jfms.engine.service.biz.remote.api;


import com.jfms.aaa.ChannelApi;
import com.jfms.aaa.model.GroupInfo;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

//url = "localhost:6070"
//serviceId = "aaa-server"
@FeignClient(name = "channel-api", serviceId = "aaa-server", fallbackFactory = ChannelApiClientFallbackFactory.class)
public interface ChannelApiClient extends ChannelApi {
}
