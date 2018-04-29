package com.jfms.engine.service.biz.remote;

import com.jfms.engine.api.model.JFMSClientSendMessage;
import com.jfms.engine.api.model.JFMSServerSendMessage;
import com.jfms.engine.service.biz.remote.model.OnlineMessageEntity;
import com.jfms.offline_message.model.OfflineMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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


    public OfflineMessage getOfflineMessage(String messageId, JFMSClientSendMessage jfmsClientSendMessage) {
        if (jfmsClientSendMessage == null)
            return null;
        OfflineMessage offlineMessage = new OfflineMessage();
        offlineMessage.setFrom(jfmsClientSendMessage.getFrom());
        offlineMessage.setTo(jfmsClientSendMessage.getTo());
        offlineMessage.setSubject(jfmsClientSendMessage.getSubject());
        offlineMessage.setSendTime(jfmsClientSendMessage.getSendTime());
        offlineMessage.setBody(jfmsClientSendMessage.getBody());
        offlineMessage.setId(messageId);
        return offlineMessage;
    }


    private JFMSServerSendMessage getJFMSServerSendMessage(OfflineMessage offlineMessage) {
        if (offlineMessage == null)
            return null;
        return new JFMSServerSendMessage(
                offlineMessage.getId(),
                offlineMessage.getFrom(),
                offlineMessage.getBody(),
                offlineMessage.getSubject(),
                offlineMessage.getSendTime()
        );
    }

    public List<JFMSServerSendMessage> getJFMSServerSendMessageList(List<OfflineMessage> offlineMessageList) {
        if (offlineMessageList == null)
            return null;
        List<JFMSServerSendMessage> jfmsServerSendMessageList = new ArrayList<>();
        for (OfflineMessage offlineMessage : offlineMessageList) {
            if (offlineMessage != null)
                jfmsServerSendMessageList.add(getJFMSServerSendMessage(offlineMessage));
        }
        return jfmsServerSendMessageList;
    }
}
