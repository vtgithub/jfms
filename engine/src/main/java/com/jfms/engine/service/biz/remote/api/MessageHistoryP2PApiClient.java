package com.jfms.engine.service.biz.remote.api;

import com.jfms.message_history.P2PHistoryApi;
import com.jfms.message_history.model.HistoryMessage;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "message-history-api", url = "localhost:7080")
public interface MessageHistoryP2PApiClient extends P2PHistoryApi{

    static HistoryMessage getP2PHistoryMessage(String messageId, String sender, String body, String subject, Long time){
        return new HistoryMessage(messageId, sender, body, subject, time);
    }
}
