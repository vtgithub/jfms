package com.jfms.engine.test;

import redis.clients.jedis.Jedis;

/**
 * Created by vahid on 4/3/18.
 */
public class PublisherTest {
    public static void main(String[] args) {
        Jedis publisher =  new Jedis();
        for (int i = 0; i < 10; i++) {
            publisher.publish("x"+i, "testMessage");

        }
    }
}
