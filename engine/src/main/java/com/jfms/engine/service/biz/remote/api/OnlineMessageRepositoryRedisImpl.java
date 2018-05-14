package com.jfms.engine.service.biz.remote.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vahid on 4/3/18.
 */
@Component
public class OnlineMessageRepositoryRedisImpl implements OnlineMessageRepository<JedisPubSub> {

//    @Value("onlineRepo.host")
//    private String hostName;
//
//    @Value("onlineRepo.port")
//    private String portNumber;

    private Jedis publisher;
    private Jedis subscriber;
//    private JedisCluster publisher;
//    private JedisCluster subscriber;

    @Autowired
    public OnlineMessageRepositoryRedisImpl(@Value("${redis.onlineMessage.nodes}") String nodes) {
//        String[] redisNodes = nodes.split(",");
//        Set<HostAndPort> nodeInfoSet =  new HashSet<>();
//        for (String redisNode : redisNodes) {
//            String[] nodeInfo = redisNode.split(":");
//            nodeInfoSet.add(new HostAndPort(nodeInfo[0], Integer.parseInt(nodeInfo[1])));
//        }
        publisher = new Jedis("localhost", Integer.parseInt("6379"));
        subscriber = new Jedis("localhost", Integer.parseInt("6379"));
//        publisher = new JedisCluster(nodeInfoSet);
//        subscriber = new JedisCluster(nodeInfoSet);
    }

    public void sendMessage(String channel, String message) {
        publisher.publish(channel , message);
    }
//
//
    public void setMessageListener(JedisPubSub messageListener)  {


        (new Thread(new Runnable() {
            @Override
            public void run() {
                subscriber.psubscribe( messageListener, "*");
            }
        })).start();

    }


}
