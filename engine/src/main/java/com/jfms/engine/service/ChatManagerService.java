package com.jfms.engine.service;

import com.google.gson.Gson;
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
//        int methodNo = fetchMethod(messageInJson);
//        if(methodNo == Method.INIT.getValue()) {
//            NakaLoginMessage nakaLoginMessage = new Gson().fromJson(messageInJson, NakaLoginMessage.class);
//            chatManager.init(nakaLoginMessage , session);
//        } else if(methodNo == Method.SEND.getValue()) {
            JFMSSendMessage jfmsSendMessage = new Gson().fromJson(messageInJson, JFMSSendMessage.class);
            chatManager.sendMessage(jfmsSendMessage);
//        }
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
