package com.jfms.engine.service.biz.remote.helper;

import com.jfms.engine.api.model.JFMSClientEditMessage;
import com.jfms.engine.api.model.JFMSClientSendMessage;
import com.jfms.message_history.model.P2PMessage;

public class HistoryMessageProducer {
    public static P2PMessage getP2PMessage(String messageId, JFMSClientSendMessage jfmsClientSendMessage){
        P2PMessage p2PMessage = new P2PMessage();
        p2PMessage.setMessageId(messageId);
        p2PMessage.setSender(jfmsClientSendMessage.getFrom());
        p2PMessage.setBody(jfmsClientSendMessage.getBody());
        p2PMessage.setSubject(jfmsClientSendMessage.getSubject());
        p2PMessage.setTime(jfmsClientSendMessage.getSendTime());
        return p2PMessage;
    }

    public static P2PMessage getP2PMessage(JFMSClientEditMessage jfmsClientEditMessage) {
        P2PMessage p2PMessage = new P2PMessage();
        p2PMessage.setMessageId(jfmsClientEditMessage.getId());
        p2PMessage.setSender(jfmsClientEditMessage.getFrom());
        p2PMessage.setBody(jfmsClientEditMessage.getBody());
        p2PMessage.setSubject(jfmsClientEditMessage.getSubject());
        p2PMessage.setTime(jfmsClientEditMessage.getEditTime());
        return p2PMessage;
    }

}
