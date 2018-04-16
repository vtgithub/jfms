package com.jfms.offlinemessage.offlinemessage.service;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

//  private static final Logger LOGGER =
//      LoggerFactory.getLogger(Receiver.class);

//  private CountDownLatch latch = new CountDownLatch(1);
//
//  public CountDownLatch getLatch() {
//    return latch;
//  }

//  @KafkaListener(topics = "${kafka.topic.helloworld}")
  public String receive(String payload) {
    System.out.println("received payload='{}'" +payload);
//    LOGGER.info("received payload='{}'", payload);
//    latch.countDown();
    return payload;
  }
}