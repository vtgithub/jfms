package com.jfms.engine.service.biz.remote.api.message_history;

import com.jfms.message_history.GroupHistoryApi;
import com.jfms.message_history.model.HistoryMessage;
import org.springframework.cloud.openfeign.FeignClient;

// url = "localhost:7080"
//serviceId = "message-history-server"
@FeignClient(name = "message-history-group-api",
        serviceId = "message-history-server",
        fallbackFactory = MessageHistoryGroupApiClientFallback.class
)
public interface MessageHistoryGroupApiClient extends GroupHistoryApi{

    static HistoryMessage getGroupHistoryMessage(String messageId, String sender, String body, String subject, Long time){
        return new HistoryMessage(messageId, sender, body, subject, time);
    }
}
