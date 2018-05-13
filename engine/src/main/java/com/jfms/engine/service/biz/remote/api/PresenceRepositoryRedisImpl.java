package com.jfms.engine.service.biz.remote.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource("classpath:redis.properties")
public class PresenceRepositoryRedisImpl implements PresenceRepository {

    private Jedis presenceHandler;
//    private JedisCluster presenceHandler;
    @Autowired
    public PresenceRepositoryRedisImpl(@Value("${redis.presence.node}") String node)
    {
//        String[] redisNodes = nodes.split(",");
//        Set<HostAndPort> nodeInfoSet =  new HashSet<>();
//        for (String redisNode : redisNodes) {
//            String[] nodeInfo = redisNode.split(":");
//            nodeInfoSet.add(new HostAndPort(nodeInfo[0], Integer.parseInt(nodeInfo[1])));
//        }
//        presenceHandler = new JedisCluster(nodeInfoSet);
        String[] nodeInfo = node.split(":");
        presenceHandler = new Jedis(nodeInfo[0], Integer.parseInt(nodeInfo[1]));
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
