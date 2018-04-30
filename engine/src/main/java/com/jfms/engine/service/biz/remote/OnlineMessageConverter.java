package com.jfms.engine.service.biz.remote;

import com.jfms.engine.api.model.JFMSServerSendMessage;
import com.jfms.engine.api.model.JFMSClientSendMessage;
import com.jfms.engine.service.biz.remote.model.OnlineMessageEntity;
import org.springframework.stereotype.Component;

/**
 * Created by vahid on 4/4/18.
 */
@Component
public class OnlineMessageConverter {

    public OnlineMessageEntity getOnlineMessageEntity(String messageId, JFMSClientSendMessage jfmsClientSendMessage){
        if (jfmsClientSendMessage == null)
            return null;
        OnlineMessageEntity onlineMessageEntity = new OnlineMessageEntity(
                messageId,
                jfmsClientSendMessage.getFrom(),
                jfmsClientSendMessage.getTo(),
                jfmsClientSendMessage.getBody(),
                jfmsClientSendMessage.getSubject(),
                jfmsClientSendMessage.getSendTime()
        );
        return onlineMessageEntity;
    }

    public JFMSServerSendMessage getJFMSReceiveMessage(OnlineMessageEntity onlineMessageEntity) {
        if (onlineMessageEntity == null)
            return null;
        JFMSServerSendMessage jfmsServerSendMessage = new JFMSServerSendMessage(
                onlineMessageEntity.getId(),
                onlineMessageEntity.getFrom(),
                onlineMessageEntity.getMessage(),
                onlineMessageEntity.getSubject(),
                onlineMessageEntity.getSendTime()
        );
        return jfmsServerSendMessage;
    }

}
