package com.jfms.engine.service;

import com.google.gson.Gson;
import com.jfms.engine.api.model.*;
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
    private
    Gson gson = new Gson();
    public void processMessage(TextMessage message , WebSocketSession session) throws MethodIsNullException {

        System.out.println(message.getPayload());

        String messageInJson = message.getPayload();
        int methodNo = fetchMethod(messageInJson);

        if(methodNo == Method.INIT.getValue()) {
            JFMSClientLoginMessage jfmsClientLoginMessage = gson.fromJson(messageInJson, JFMSClientLoginMessage.class);
            chatManager.init(jfmsClientLoginMessage, session);
        } else if(methodNo == Method.SEND.getValue()) {
            JFMSClientSendMessage jfmsClientSendMessage = gson.fromJson(messageInJson, JFMSClientSendMessage.class);
            chatManager.sendMessage(jfmsClientSendMessage, session);
        } else if (methodNo == Method.EDIT.getValue()){
            JFMSClientEditMessage jfmsClientEditMessage = gson.fromJson(messageInJson, JFMSClientEditMessage.class);
            chatManager.editMessage(jfmsClientEditMessage);
        } else if (methodNo == Method.DELETE.getValue()){
            JFMSClientDeleteMessage jfmsClientDeleteMessage = gson.fromJson(messageInJson, JFMSClientDeleteMessage.class);
            chatManager.deleteMessage(jfmsClientDeleteMessage);
        } else if (methodNo == Method.IS_TYPING.getValue()){
            JFMSClientIsTypingMessage jfmsClientIsTypingMessage = gson.fromJson(messageInJson, JFMSClientIsTypingMessage.class);
            chatManager.isTypingMessage(jfmsClientIsTypingMessage);
        } else if (methodNo == Method.PING.getValue()){
            JFMSClientPingMessage jfmsClientPingMessage =
                    gson.fromJson(messageInJson, JFMSClientPingMessage.class);
            chatManager.updatePresenceTime(jfmsClientPingMessage, session);
        } else if (methodNo == Method.CONVERSATION_LEAVE.getValue()){
            JFMSClientConversationLeaveMessage jfmsClientConversationLeaveMessage =
                    gson.fromJson(messageInJson, JFMSClientConversationLeaveMessage.class);
            chatManager.setLeaveTime(jfmsClientConversationLeaveMessage);
        } else if (methodNo == Method.CONVERSATION_IN.getValue()){
            JFMSClientConversationInMessage jfmsClientConversationInMessage =
                    gson.fromJson(messageInJson, JFMSClientConversationInMessage.class);
            chatManager.getLeaveTime(jfmsClientConversationInMessage, session);
        } else if (methodNo == Method.SEEN.getValue()){
            JFMSClientSeenMessage jfmsClientSeenMessage =
                    gson.fromJson(messageInJson, JFMSClientSeenMessage.class);
            chatManager.setSeen(jfmsClientSeenMessage);
        } else if (methodNo == Method.GROUP_CREATION.getValue()){
            JFMSClientGroupCreationMessage jfmsClientGroupCreationMessage =
                    gson.fromJson(messageInJson, JFMSClientGroupCreationMessage.class);
            chatManager.createGroup(jfmsClientGroupCreationMessage, session);
        } else if (methodNo == Method.GROUP_SEND.getValue()){
            JFMSClientSendMessage jfmsClientGroupSendMessage =
                    gson.fromJson(messageInJson, JFMSClientSendMessage.class);
            chatManager.sendGroupMessage(jfmsClientGroupSendMessage, session);
        } else if (methodNo == Method.GROUP_EDIT.getValue()){
            JFMSClientEditMessage jfmsClientGroupEditMessage =
                    gson.fromJson(messageInJson, JFMSClientEditMessage.class);
            chatManager.editGroupMessage(jfmsClientGroupEditMessage);
        } else if (methodNo == Method.GROUP_DELETE.getValue()){
            JFMSClientDeleteMessage jfmsClientGroupDeleteMessage=
                    gson.fromJson(messageInJson, JFMSClientDeleteMessage.class);
            chatManager.deleteGroupMessage(jfmsClientGroupDeleteMessage);
        } else if (methodNo == Method.GROUP_IS_TYPING.getValue()){
            JFMSClientIsTypingMessage jfmsClientIsTypingMessage =
                    gson.fromJson(messageInJson, JFMSClientIsTypingMessage.class);
            chatManager.groupIsTypingMessage(jfmsClientIsTypingMessage);
        } else if (methodNo == Method.GROUP_CONVERSATION_LEAVE.getValue()){
            JFMSClientConversationLeaveMessage jfmsClientConversationLeaveMessage =
                    gson.fromJson(messageInJson, JFMSClientConversationLeaveMessage.class);
            chatManager.setGroupLeaveTime(jfmsClientConversationLeaveMessage);
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
