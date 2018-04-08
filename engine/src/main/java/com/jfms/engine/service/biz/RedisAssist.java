package com.jfms.engine.service.biz;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSReceiveMessage;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.model.RedisChannelEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by vahid on 4/3/18.
 */
@Component
public class RedisAssist implements InitializingBean {
//    private @Value("onlineRepo.host") String hostName;
//    private @Value("onlineRepo.port") String portNumber;

    @Autowired
    UserSessionRepository userSessionRepository;
    @Autowired
    RedisConverter redisConverter;
//
    private Jedis publisher;
    private Jedis subscriber;
//
    public void sendMessage( String channel, String message) {
        publisher.publish(channel , message);
    }
//
//
    @Override
    public void afterPropertiesSet() throws Exception {
        publisher = new Jedis("localhost", 6379);
        subscriber = new Jedis("localhost", 6379);
        Gson gson = new Gson();
        (new Thread(new Runnable() {
            @Override
            public void run() {
                subscriber.psubscribe(new JedisPubSub() {
                    @Override
                    public void onPMessage(String pattern, String channel, String message) {
                    RedisChannelEntity redisChannelEntity = gson.fromJson(message, RedisChannelEntity.class);
                    WebSocketSession session = userSessionRepository.getSession(redisChannelEntity.getTo());
                    JFMSReceiveMessage jfmsReceiveMessage = redisConverter.getJFMSReceiveMessage(redisChannelEntity);
                    try {
                        session.sendMessage(new TextMessage(gson.toJson(jfmsReceiveMessage)));
                    } catch (IOException e) {
                        //todo log
                        e.printStackTrace();
                    }
                        }
                    }, "*");
            }
        })).start();

    }


}
