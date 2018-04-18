package com.jfms.engine.service.biz.remote;

import com.jfms.engine.service.biz.remote.model.OnlineMessageEntity;
import com.jfms.offline_message.model.OfflineMessage;
import org.springframework.stereotype.Component;

@Component
public class OfflineMessageConverter {
    public OfflineMessage getOfflineMessage(OnlineMessageEntity onlineMessageEntity){
        if (onlineMessageEntity == null)
            return null;
        OfflineMessage offlineMessage = new OfflineMessage();
        offlineMessage.setFrom(onlineMessageEntity.getFrom());
        offlineMessage.setTo(onlineMessageEntity.getTo());
        offlineMessage.setBody(onlineMessageEntity.getMessage());
        offlineMessage.setSendTime(onlineMessageEntity.getSendTime());
        offlineMessage.setSubject(onlineMessageEntity.getSubject());
        return offlineMessage;
    }
}
