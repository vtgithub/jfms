package com.jfms.engine.api;

import com.jfms.engine.service.ChatManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {

	@Autowired
	ChatManagerService chatManagerService;
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		try {
			chatManagerService.processMessage(message , session);
//			session.sendMessage(new TextMessage("response1"));
		}catch (Exception e){
			//TODO set aspect on this class
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("connect");
		//the messages will be broadcasted to all users.
		//sessions.add(session);
	}

	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		chatManagerService.closeUserSession(session.getId());
	}

}