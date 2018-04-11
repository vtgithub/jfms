package com.jfms.engine.api.converter;

import com.jfms.engine.api.model.*;
import org.springframework.stereotype.Component;

/**
 * Created by vahid on 4/9/18.
 */
@Component
public class JFMSMessageConverter {
    public JFMSServerEditMessage JFMSClientEditToJFMSServerEdit(JFMSClientEditMessage jfmsClientEditMessage){
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

    public JFMSServerDeleteMessage JFMSClientDeleteToJFMSServerDelete(JFMSClientDeleteMessage jfmsClientDeleteMessage) {
        if (jfmsClientDeleteMessage == null)
            return null;
        return new JFMSServerDeleteMessage(
                    jfmsClientDeleteMessage.getId(),
                    jfmsClientDeleteMessage.getFrom()
                );
    }

    public JFMSServerIsTypingMessage JFMSClientIsTypingToJFMSServerIsTyping(JFMSClientIsTypingMessage jfmsClientIsTypingMessage) {
        if (jfmsClientIsTypingMessage == null)
            return null;
        return new JFMSServerIsTypingMessage(jfmsClientIsTypingMessage.getFrom());
    }

    public JFMSServerConversationMessage JFMSClientConversationLeaveToJFMSServerConversation(
            JFMSClientConversationLeaveMessage jfmsClientConversationLeaveMessage) {
        if (jfmsClientConversationLeaveMessage == null)
            return null;
        return new JFMSServerConversationMessage(
                jfmsClientConversationLeaveMessage.getFrom(),
                jfmsClientConversationLeaveMessage.getLeaveTime()
        );
    }
}
