package com.jfms.engine.service.biz.remote.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class PresenceRepositoryRedisImpl implements PresenceRepository {
    private Jedis presenceHandler;
    @Autowired
    public PresenceRepositoryRedisImpl(){
        presenceHandler = new Jedis("localhost", Integer.parseInt("6379"));
    }


    public void changePresenceTime(String from, Long pingTime) {
        presenceHandler.hset("presence", from, pingTime.toString());
    }

    public void setPresenceStatus(String user, String status){
        presenceHandler.hset("presenceStatus",user, status);
    }

    public String getPresenceStatus(String user){
        return presenceHandler.hget("presenceStatus", user);
    }

}
