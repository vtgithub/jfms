package com.jfms.engine.service.biz.remote.api;

import com.jfms.offline_message.OfflineMessageApi;
import com.jfms.offline_message.model.OfflineMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


// url = "localhost:7070"
// serviceId = "offline-message-server"
@FeignClient(name = "offline-message-api",
        serviceId = "offline-message-server", fallbackFactory = OfflineMessageApiClientFallbackFactory.class
)
@RequestMapping(value = "/offline", produces = "application/json", consumes = "application/json")
public interface OfflineMessageApiClient extends OfflineMessageApi {

    // ----- helper
    static OfflineMessage getOfflineMessage(String owner, String message){
        OfflineMessage offlineMessage = new OfflineMessage();
        offlineMessage.setOwner(owner);
        offlineMessage.setMessage(message);
        return offlineMessage;
    }
}




