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
    public void setLastSeen(String sourceUser, String destinationUser, Long seenTime) {
        redisDao.hset("last_seen_map", sourceUser+destinationUser, seenTime.toString());
    }

    @Override
    public Long getLastSeen(String sourceUser, String destinationUser) {
        String lastSeenTime = redisDao.hget("last_seen_map", destinationUser+sourceUser);
        return Long.parseLong(lastSeenTime);
    }

}
