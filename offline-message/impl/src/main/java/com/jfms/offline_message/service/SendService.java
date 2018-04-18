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
  private Gson gson = new Gson();

  public void send(String topic, OfflineMessage offlineMessage) {
    System.out.println("message sent from "+ offlineMessage.getFrom() + " to " + offlineMessage.getTo());
    kafkaTemplate.send(topic, gson.toJson(offlineMessage));
  }
}