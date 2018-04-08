package com.jfms.engine.dal;

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

    public void removeBySession(String sessionId){
        Iterator<Map.Entry<String, WebSocketSession>> iterator = userSessionMap.entrySet().iterator();
        while (iterator.hasNext()){
            if (iterator.next().getValue().getId().trim().equals(sessionId.trim())){
                iterator.remove();
            }
        }
    }

}
