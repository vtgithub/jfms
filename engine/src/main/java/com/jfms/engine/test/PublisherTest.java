package com.jfms.engine.test;

import redis.clients.jedis.Jedis;

import java.util.Base64;

/**
 * Created by vahid on 4/3/18.
 */
public class PublisherTest {
    public static void main(String[] args) {
        Jedis publisher =  new Jedis();
        publisher.publish("x" , "testMessage");
    }


    public static String concatString(String s1, String s2){
        if (s1.compareTo(s2) >= 0)
            return s1 + s2;
        else
            return s2 + s1;

    }
}
