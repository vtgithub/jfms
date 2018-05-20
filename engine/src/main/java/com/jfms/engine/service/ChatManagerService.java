package com.jfms.engine.service;

import com.google.gson.Gson;
import com.jfms.engine.api.model.*;
import com.jfms.engine.api.Method;
import com.jfms.engine.service.biz.GroupChatManager;
import com.jfms.engine.service.biz.P2PChatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Component
public class ChatManagerService {

    @Autowired
    private P2PChatManager p2PChatManager;
    @Autowired
    private GroupChatManager groupChatManager;
    private Gson gson = new Gson();

    public void processMessage(TextMessage message , WebSocketSession session) throws MethodIsNullException {
        System.out.println(message.getPayload());
        String messageInJson = message.getPayload();
        int methodNo = fetchMethod(messageInJson);

        if(methodNo == Method.INIT.getValue()) {
            JFMSClientLoginMessage jfmsClientLoginMessage = gson.fromJson(messageInJson, JFMSClientLoginMessage.class);
            p2PChatManager.init(jfmsClientLoginMessage, session);
        } else if(methodNo == Method.SEND.getValue()) {
            JFMSClientSendMessage jfmsClientSendMessage = gson.fromJson(messageInJson, JFMSClientSendMessage.class);
            p2PChatManager.sendMessage(jfmsClientSendMessage, session);
        } else if (methodNo == Method.EDIT.getValue()){
            JFMSClientEditMessage jfmsClientEditMessage = gson.fromJson(messageInJson, JFMSClientEditMessage.class);
            p2PChatManager.editMessage(jfmsClientEditMessage);
        } else if (methodNo == Method.DELETE.getValue()){
            JFMSClientDeleteMessage jfmsClientDeleteMessage = gson.fromJson(messageInJson, JFMSClientDeleteMessage.class);
            p2PChatManager.deleteMessage(jfmsClientDeleteMessage);
        } else if (methodNo == Method.IS_TYPING.getValue()){
            JFMSClientIsTypingMessage jfmsClientIsTypingMessage = gson.fromJson(messageInJson, JFMSClientIsTypingMessage.class);
            p2PChatManager.isTypingMessage(jfmsClientIsTypingMessage);
        } else if (methodNo == Method.PING.getValue()){
            JFMSClientPingMessage jfmsClientPingMessage =
                    gson.fromJson(messageInJson, JFMSClientPingMessage.class);
            p2PChatManager.updatePresenceTime(jfmsClientPingMessage, session);
        } else if (methodNo == Method.CONVERSATION_LEAVE.getValue()){
            JFMSClientConversationLeaveMessage jfmsClientConversationLeaveMessage =
                    gson.fromJson(messageInJson, JFMSClientConversationLeaveMessage.class);
            p2PChatManager.setLeaveTime(jfmsClientConversationLeaveMessage);
        } else if (methodNo == Method.CONVERSATION_IN.getValue()){
            JFMSClientConversationInMessage jfmsClientConversationInMessage =
                    gson.fromJson(messageInJson, JFMSClientConversationInMessage.class);
            p2PChatManager.getLeaveTime(jfmsClientConversationInMessage, session);
        } else if (methodNo == Method.SEEN.getValue()){
            JFMSClientSeenMessage jfmsClientSeenMessage =
                    gson.fromJson(messageInJson, JFMSClientSeenMessage.class);
            p2PChatManager.setSeen(jfmsClientSeenMessage);
        } else if (methodNo == Method.GROUP_CREATION.getValue()){
            JFMSClientGroupCreationMessage jfmsClientGroupCreationMessage =
                    gson.fromJson(messageInJson, JFMSClientGroupCreationMessage.class);
            groupChatManager.createGroup(jfmsClientGroupCreationMessage, session);
        } else if (methodNo == Method.GROUP_SEND.getValue()){
            JFMSClientSendMessage jfmsClientGroupSendMessage =
                    gson.fromJson(messageInJson, JFMSClientSendMessage.class);
            groupChatManager.sendGroupMessage(jfmsClientGroupSendMessage, session);
        } else if (methodNo == Method.GROUP_EDIT.getValue()){
            JFMSClientEditMessage jfmsClientGroupEditMessage =
                    gson.fromJson(messageInJson, JFMSClientEditMessage.class);
            groupChatManager.editGroupMessage(jfmsClientGroupEditMessage);
        } else if (methodNo == Method.GROUP_DELETE.getValue()){
            JFMSClientDeleteMessage jfmsClientGroupDeleteMessage=
                    gson.fromJson(messageInJson, JFMSClientDeleteMessage.class);
            groupChatManager.deleteGroupMessage(jfmsClientGroupDeleteMessage);
        } else if (methodNo == Method.GROUP_IS_TYPING.getValue()){
            JFMSClientIsTypingMessage jfmsClientIsTypingMessage =
                    gson.fromJson(messageInJson, JFMSClientIsTypingMessage.class);
            groupChatManager.groupIsTypingMessage(jfmsClientIsTypingMessage);
        } else if (methodNo == Method.GROUP_CONVERSATION_LEAVE.getValue()){
            JFMSClientConversationLeaveMessage jfmsClientConversationLeaveMessage =
                    gson.fromJson(messageInJson, JFMSClientConversationLeaveMessage.class);
            groupChatManager.setGroupLeaveTime(jfmsClientConversationLeaveMessage);
        } else if (methodNo == Method.GROUP_INFO_EDIT.getValue()){
            JFMSClientGroupInfoEditMessage jfmsClientGroupInfoEditMessage =
                    gson.fromJson(messageInJson, JFMSClientGroupInfoEditMessage.class);
            groupChatManager.editGroupInfo(jfmsClientGroupInfoEditMessage);
        }
    }

    public void closeUserSession(String sessionId){
        p2PChatManager.removeUserSession(sessionId);
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
