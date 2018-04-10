package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSServerSendMessage;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.model.RedisChannelEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vahid on 4/3/18.
 */
@Component
public class RedisAssist  {

//    @Value("onlineRepo.host")
//    private String hostName;
//
//    @Value("onlineRepo.port")
//    private String portNumber;

    private Jedis publisher;
    private Jedis subscriber;
    private Jedis presenceHandler;

    @Autowired
    public RedisAssist() {
        publisher = new Jedis("localhost", Integer.parseInt("6379"));
        subscriber = new Jedis("localhost", Integer.parseInt("6379"));
        presenceHandler = new Jedis("localhost", Integer.parseInt("6379"));
    }

    public void sendMessage(String channel, String message) {
        publisher.publish(channel , message);
    }
//
//
    public void setMessageListener(JedisPubSub messageListener) throws Exception {

        (new Thread(new Runnable() {
            @Override
            public void run() {
                subscriber.psubscribe( messageListener, "*");
            }
        })).start();

    }


    public void changePresenceTime(String from, Long pingTime) {
        presenceHandler.hset("presence", from, pingTime.toString());
    }
}
