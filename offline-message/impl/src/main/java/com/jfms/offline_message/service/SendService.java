package com.jfms.offline_message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendService {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void send(String topic, String offlineMessageInJson) {
    System.out.println("sending payload="+offlineMessageInJson+ "to topic="+ topic);
    kafkaTemplate.send(topic, offlineMessageInJson);
  }
}