package com.jfms.engine.service.biz.remote;

import com.google.gson.Gson;
import com.jfms.engine.api.model.JFMSServerSendMessage;
import com.jfms.engine.dal.UserSessionRepository;
import com.jfms.engine.service.biz.remote.api.OfflineMessageApiClient;
import com.jfms.engine.service.biz.remote.model.OnlineMessageEntity;
import com.jfms.offline_message.OfflineMessageApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;

@Component
public class OnlineMessageListener extends JedisPubSub {

    UserSessionRepository userSessionRepository;
    OnlineMessageConverter onlineMessageConverter;
//    OfflineMessageConverter offlineMessageConverter;
//    OfflineMessageApiClient offlineMessageApiClient;

    Gson gson = new Gson();
    @Override
    @HystrixCommand(fallbackMethod = "fallBack")
    public void onPMessage(String pattern, String channel, String message) {
        OnlineMessageEntity onlineMessageEntity = gson.fromJson(message, OnlineMessageEntity.class);
        WebSocketSession session = userSessionRepository.getSession(onlineMessageEntity.getTo());
        if (session == null) {
//            try{
//                offlineMessageApiClient.produceMessage(offlineMessageConverter.getOfflineMessage(onlineMessageEntity));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }else{
            JFMSServerSendMessage jfmsServerSendMessage = onlineMessageConverter.getJFMSReceiveMessage(onlineMessageEntity);
            try {
                session.sendMessage(new TextMessage(gson.toJson(jfmsServerSendMessage)));
            } catch (IOException e) {
                //todo log
                e.printStackTrace();
            }
        }
    }

    public void init(OnlineMessageConverter onlineMessageConverter,
                     UserSessionRepository userSessionRepository) {
//        this.offlineMessageConverter = offlineMessageConverter;
//        this.offlineMessageApiClient = offlineMessageApiClient;
        this.onlineMessageConverter = onlineMessageConverter;
        this.userSessionRepository = userSessionRepository;
    }

    public String fallBack(){
        return "exception in calling offline-message microservice";
    }
}
