package com.jfms.engine.api.converter;

import com.jfms.engine.api.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahid on 4/9/18.
 */
@Component
public class JFMSMessageConverter {
    public JFMSServerEditMessage clientEditToServerEdit(JFMSClientEditMessage jfmsClientEditMessage){
        if (jfmsClientEditMessage == null)
            return null;
        return new JFMSServerEditMessage(
                jfmsClientEditMessage.getId(),
                jfmsClientEditMessage.getFrom(),
                jfmsClientEditMessage.getBody(),
                jfmsClientEditMessage.getSubject(),
                jfmsClientEditMessage.getEditTime()
        );
    }

    public JFMSServerDeleteMessage clientDeleteToServerDelete(JFMSClientDeleteMessage jfmsClientDeleteMessage) {
        if (jfmsClientDeleteMessage == null)
            return null;
        return new JFMSServerDeleteMessage(
                    jfmsClientDeleteMessage.getId(),
                    jfmsClientDeleteMessage.getFrom()
                );
    }

    public JFMSServerIsTypingMessage clientIsTypingToServerIsTyping(JFMSClientIsTypingMessage jfmsClientIsTypingMessage) {
        if (jfmsClientIsTypingMessage == null)
            return null;
        return new JFMSServerIsTypingMessage(jfmsClientIsTypingMessage.getFrom());
    }

    public JFMSServerConversationMessage clientConversationLeaveToServerConversation(
            JFMSClientConversationLeaveMessage jfmsClientConversationLeaveMessage) {
        if (jfmsClientConversationLeaveMessage == null)
            return null;
        return new JFMSServerConversationMessage(
                jfmsClientConversationLeaveMessage.getFrom(),
                jfmsClientConversationLeaveMessage.getLeaveTime()
        );
    }

    public JFMSServerSeenMessage clientSeentoServerSeen(JFMSClientSeenMessage jfmsClientSeenMessage) {
        if (jfmsClientSeenMessage == null)
            return null;
        return new JFMSServerSeenMessage(
                jfmsClientSeenMessage.getMessageIdList(),
                jfmsClientSeenMessage.getSeenTime(),
                jfmsClientSeenMessage.getFrom()
        );
    }

    public JFMSServerSendMessage clientSendToServerSend(String messageId, JFMSClientSendMessage jfmsClientSendMessage) {
        return new JFMSServerSendMessage(
                messageId,
                jfmsClientSendMessage.getFrom(),
                jfmsClientSendMessage.getBody(),
                jfmsClientSendMessage.getSubject(),
                jfmsClientSendMessage.getSendTime()
        );
    }
}
