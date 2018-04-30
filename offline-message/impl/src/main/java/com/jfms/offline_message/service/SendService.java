package com.jfms.offline_message.service;

import com.google.gson.Gson;
import com.jfms.offline_message.model.OfflineMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendService {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void send(String topic, OfflineMessage offlineMessage) {
    System.out.println("message saved for "+ offlineMessage.getOwner());
    //todo log
    kafkaTemplate.send(topic, offlineMessage.getMessage());
  }
}