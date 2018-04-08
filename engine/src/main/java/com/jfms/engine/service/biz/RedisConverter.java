package com.jfms.engine.service.biz;

import com.jfms.engine.api.model.JFMSServerSendMessage;
import com.jfms.engine.api.model.JFMSClientSendMessage;
import com.jfms.engine.service.biz.model.RedisChannelEntity;
import org.springframework.stereotype.Component;

/**
 * Created by vahid on 4/4/18.
 */
@Component
public class RedisConverter {

    RedisChannelEntity getRedisChannelEntity(JFMSClientSendMessage jfmsClientSendMessage){
        if (jfmsClientSendMessage == null)
            return null;
        RedisChannelEntity redisChannelEntity = new RedisChannelEntity(
                jfmsClientSendMessage.getFrom(),
                jfmsClientSendMessage.getTo(),
                jfmsClientSendMessage.getBody(),
                jfmsClientSendMessage.getSubject(),
                jfmsClientSendMessage.getSendTime()
        );
        return redisChannelEntity;
    }

    public JFMSServerSendMessage getJFMSReceiveMessage(RedisChannelEntity redisChannelEntity) {
        if (redisChannelEntity == null)
            return null;
        JFMSServerSendMessage jfmsServerSendMessage = new JFMSServerSendMessage(
                redisChannelEntity.getId(),
                redisChannelEntity.getFrom(),
                redisChannelEntity.getMessage(),
                redisChannelEntity.getSubject(),
                redisChannelEntity.getSendTime()
        );
        return jfmsServerSendMessage;
    }
}
