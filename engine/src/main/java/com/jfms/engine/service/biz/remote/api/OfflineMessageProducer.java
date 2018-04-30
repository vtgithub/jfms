package com.jfms.engine.service.biz.remote.api;

import com.jfms.offline_message.model.OfflineMessage;

public class OfflineMessageProducer {
    public static OfflineMessage getOfflineMessage(String owner, String message){
        OfflineMessage offlineMessage = new OfflineMessage();
        offlineMessage.setOwner(owner);
        offlineMessage.setMessage(message);
        return offlineMessage;
    }
}
