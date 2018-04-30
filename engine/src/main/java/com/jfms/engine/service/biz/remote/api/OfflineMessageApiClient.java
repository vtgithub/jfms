package com.jfms.engine.service.biz.remote.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "offline-message-api", url = "localhost:7070")
public interface OfflineMessageApiClient extends OfflineMessageApi{
}
