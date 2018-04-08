package com.jfms.engine.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by vahid on 4/3/18.
 */
public class SubscriberTest {
    public static void main(String[] args) {
        Jedis subscriber = new Jedis();
        subscriber.psubscribe(new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.println(message);
                System.out.println(channel);
            }
        }, "*");

    }
}
