package com.jfms.engine.dal;

import com.jfms.engine.service.biz.UserStatus;
import com.jfms.engine.service.biz.remote.api.PresenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by vahid on 4/4/18.
 */
@Repository
public class UserSessionRepository {
    Map<String, WebSocketSession> userSessionMap = new HashMap<String, WebSocketSession>();

    public WebSocketSession getSession(String user){
        return userSessionMap.get(user);
    }

    public void addSession(String user, WebSocketSession session){
        userSessionMap.put(user, session);
    }

    public void removeBySession(String sessionId, PresenceRepository presenceRepository){
        Iterator<Map.Entry<String, WebSocketSession>> iterator = userSessionMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, WebSocketSession> sessionMap= iterator.next();
            if (sessionMap.getValue().getId().trim().equals(sessionId.trim())){
                presenceRepository.setPresenceStatus(sessionMap.getKey(), UserStatus.OFFLINE.getValue());
                iterator.remove();
            }
        }
    }

}
