package com.jfms.engine.service.biz;

import com.jfms.engine.api.model.JFMSClientDeleteMessage;
import com.jfms.engine.api.model.JFMSClientEditMessage;
import com.jfms.engine.api.model.JFMSServerDeleteMessage;
import com.jfms.engine.api.model.JFMSServerEditMessage;
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
        return new JFMSServerDeleteMessage(jfmsClientDeleteMessage.getId());
    }
}
