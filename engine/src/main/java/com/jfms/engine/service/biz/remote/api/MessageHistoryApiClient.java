package com.jfms.engine.service.biz.remote.api;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "message-history-api", url = "localhost:7080")
public interface MessageHistoryApiClient{
}
