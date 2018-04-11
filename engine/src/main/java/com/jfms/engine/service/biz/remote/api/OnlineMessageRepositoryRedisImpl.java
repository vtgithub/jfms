package com.jfms.engine.service.biz.remote.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

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

    @Autowired
    public OnlineMessageRepositoryRedisImpl() {
        publisher = new Jedis("localhost", Integer.parseInt("6379"));
        subscriber = new Jedis("localhost", Integer.parseInt("6379"));

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
