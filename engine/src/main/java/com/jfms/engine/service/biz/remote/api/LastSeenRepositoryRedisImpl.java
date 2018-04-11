package com.jfms.engine.service.biz.remote.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class LastSeenRepositoryRedisImpl implements LastSeenRepository{

    private Jedis redisDao;

    @Autowired
    public LastSeenRepositoryRedisImpl(){
        this.redisDao = new Jedis("localhost", 6379);
    }

    @Override
    public void setLastSeen(String room, Long seenTime) {
        redisDao.hset("last_seen_hash", room, seenTime.toString());
    }

    @Override
    public Long getLastSeen(String room) {
        String lastSeenTime = redisDao.hget("last_seen_hash", room);
        return Long.parseLong(lastSeenTime);
    }

}
