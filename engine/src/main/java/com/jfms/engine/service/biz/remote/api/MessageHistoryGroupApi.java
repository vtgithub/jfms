package com.jfms.engine.service.biz.remote.api;

import com.jfms.message_history.GroupHistoryApi;
import com.jfms.message_history.model.HistoryMessage;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "message-history-api", url = "localhost:7080")
public interface MessageHistoryGroupApi extends GroupHistoryApi{

    static HistoryMessage getGroupHistoryMessage(String messageId, String sender, String body, String subject, Long time){
        return new HistoryMessage(messageId, sender, body, subject, time);
    }
}