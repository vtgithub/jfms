package com.jfms.engine.service.biz.remote.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import com.jfms.message_history.MessageHistoryApi;
@FeignClient(name = "message-history-api", url = "localhost:7080")
public interface MessageHistoryApiClient extends MessageHistoryApi{
}
