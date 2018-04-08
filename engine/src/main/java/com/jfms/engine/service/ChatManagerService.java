package com.jfms.engine.service;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSClientEditMessage;
import com.jfms.engine.api.model.JFMSClientLoginMessage;
import com.jfms.engine.api.model.JFMSClientSendMessage;
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
            JFMSClientLoginMessage jfmsClientLoginMessage = new Gson().fromJson(messageInJson, JFMSClientLoginMessage.class);
            chatManager.init(jfmsClientLoginMessage, session);
        } else if(methodNo == Method.SEND.getValue()) {
            JFMSClientSendMessage jfmsClientSendMessage = new Gson().fromJson(messageInJson, JFMSClientSendMessage.class);
            chatManager.sendMessage(jfmsClientSendMessage);
        } else if (methodNo == Method.EDIT.getValue()){
            JFMSClientEditMessage jfmsClientEditMessage = new Gson().fromJson(messageInJson, JFMSClientEditMessage.class);
            chatManager.editMessage(jfmsClientEditMessage);
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
