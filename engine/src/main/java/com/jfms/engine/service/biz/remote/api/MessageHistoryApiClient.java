package com.jfms.engine.service.biz.remote.api;

import com.jfms.message_history.model.P2PMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import com.jfms.message_history.MessageHistoryApi;
@FeignClient(name = "message-history-api", url = "localhost:7080")
public interface MessageHistoryApiClient extends MessageHistoryApi{

    static P2PMessage getP2PMessage(String messageId, String sender, String body, String subject, Long time){
        return new P2PMessage(messageId, sender, body, subject, time);
    }
}
