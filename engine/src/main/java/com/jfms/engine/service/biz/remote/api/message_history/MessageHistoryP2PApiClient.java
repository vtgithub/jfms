package com.jfms.engine.service.biz.remote.api.message_history;

import com.jfms.message_history.P2PHistoryApi;
import com.jfms.message_history.model.HistoryMessage;
import org.springframework.cloud.openfeign.FeignClient;

//url = "localhost:7080"
//serviceId = "message-history-server"
@FeignClient(name = "message-history-api",
        serviceId = "message-history-server",
        fallbackFactory = MessageHistoryP2PApiClientFallback.class
)
public interface MessageHistoryP2PApiClient extends P2PHistoryApi{

    static HistoryMessage getP2PHistoryMessage(String messageId, String sender, String body, String subject, Long time){
        return new HistoryMessage(messageId, sender, body, subject, time);
    }
}
