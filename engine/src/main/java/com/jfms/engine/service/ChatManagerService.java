package com.jfms.engine.service;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSLoginMessage;
import com.jfms.engine.api.model.JFMSSendMessage;
import com.jfms.engine.api.Method;
import com.jfms.engine.service.biz.ChatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Component
public class ChatManagerService {

    private @Autowired
    ChatManager chatManager;

    public void processMessage(TextMessage message , WebSocketSession session) throws MethodIsNullException {

        System.out.println(message.getPayload());

        String messageInJson = message.getPayload();
        int methodNo = fetchMethod(messageInJson);
        if(methodNo == Method.INIT.getValue()) {
            JFMSLoginMessage jfmsLoginMessage= new Gson().fromJson(messageInJson, JFMSLoginMessage.class);
            chatManager.init(jfmsLoginMessage , session);
        } else if(methodNo == Method.SEND.getValue()) {
            JFMSSendMessage jfmsSendMessage = new Gson().fromJson(messageInJson, JFMSSendMessage.class);
            chatManager.sendMessage(jfmsSendMessage);
        }
    }

    public void closeUserSession(String sessionId){
        chatManager.removeUserSession(sessionId);
    }

//    //----------------------------------

    private int fetchMethod(String messageInJson) throws MethodIsNullException{
        Integer methodNo
                = Integer.parseInt(String.valueOf(new Gson().fromJson(messageInJson, Map.class).get("method")));
        if(methodNo == null){
            throw new MethodIsNullException();
        }
        return methodNo;
    }
}
