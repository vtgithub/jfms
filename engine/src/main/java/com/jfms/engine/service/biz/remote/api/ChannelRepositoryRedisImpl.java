package com.jfms.engine.service.biz.remote.api;

import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
@PropertySource("classpath:redis.properties")
public class ChannelRepositoryRedisImpl implements ChannelRepository {
    private Jedis redisDao;

    @Autowired
    public ChannelRepositoryRedisImpl(@Value("${redis.channelInfo.node}") String node){
        String[] nodeInfo = node.split(":");
        this.redisDao = new Jedis(nodeInfo[0], Integer.parseInt(nodeInfo[1]));
    }

    @Override
    public void saveChannelInfo(String channelId, GroupInfoEntity channelInfoEntity) {
        redisDao.hset("channel_info".getBytes(), channelId.getBytes(), SerializationUtils.serialize(channelInfoEntity));
    }

    @Override
    public GroupInfoEntity getChannelInfo(String channelId) {
        byte[] channelInfoBytes = redisDao.hget("channel_info".getBytes(), channelId.getBytes());
        GroupInfoEntity channelInfoEntity = (GroupInfoEntity) SerializationUtils.deserialize(channelInfoBytes);
        return channelInfoEntity;
    }
}
