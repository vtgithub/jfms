package com.jfms.engine.service.biz.remote.api;

import com.jfms.offline_message.OfflineMessageApi;
import com.jfms.offline_message.model.OfflineMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
// url = "localhost:7070"
// serviceId = "offline-message-server"
@FeignClient(name = "offline-message-api", url = "localhost:7070")
public interface OfflineMessageApiClient extends OfflineMessageApi {

    static OfflineMessage getOfflineMessage(String owner, String message){
        OfflineMessage offlineMessage = new OfflineMessage();
        offlineMessage.setOwner(owner);
        offlineMessage.setMessage(message);
        return offlineMessage;
    }
}

