package com.jfms.offline_message.service;

import com.google.gson.Gson;
import com.jfms.offline_message.model.OfflineMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by vahid on 4/16/18.
 */
@Component
@EnableKafka
public class ReceiveService {

    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
//    @Autowired
    private KafkaConsumer<String, String> receiver;
    private Properties props;
    @Autowired
    public ReceiveService(@Value("${kafka.bootstrap-servers}") String serverAddress){
        props = new Properties();

        props.put("bootstrap.servers", serverAddress);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        receiver= new KafkaConsumer<String, String>(props);


    }

    public List<String> receive(String topic){
        kafkaListenerEndpointRegistry.stop();
        List<String> offlineMessageInJsonList = new ArrayList<>();
        receiver.subscribe(Arrays.asList(topic));
        ConsumerRecords<String, String> messageRecords = receiver.poll(0);
        for (ConsumerRecord<String, String> messageRecord: messageRecords){
            offlineMessageInJsonList.add(messageRecord.value());
        }
        return offlineMessageInJsonList;
    }
}
