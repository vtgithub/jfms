package com.jfms.engine.service.biz.remote.api;

import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class GroupRepositoryRedisImpl implements GroupRepository {
    private Jedis redisDao;

    @Autowired
    public GroupRepositoryRedisImpl(){
        this.redisDao = new Jedis("localhost", 6379);
    }

    @Override
    public void saveGroupInfo(String groupId, GroupInfoEntity groupInfoEntity) {
        redisDao.hset("group_info".getBytes(), groupId.getBytes(), SerializationUtils.serialize(groupInfoEntity));
    }

    @Override
    public GroupInfoEntity getGroupInfo(String groupId) {
        byte[] groupInfoBytes = redisDao.hget("group_info".getBytes(), groupId.getBytes());
        GroupInfoEntity groupInfoEntity = (GroupInfoEntity) SerializationUtils.deserialize(groupInfoBytes);
        return groupInfoEntity;
    }
}
