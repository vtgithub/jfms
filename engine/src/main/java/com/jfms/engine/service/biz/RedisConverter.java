package com.jfms.engine.service.biz;

import com.jfms.engine.api.model.JFMSReceiveMessage;
import com.jfms.engine.api.model.JFMSSendMessage;
import com.jfms.engine.service.biz.model.RedisChannelEntity;
import org.springframework.stereotype.Component;

/**
 * Created by vahid on 4/4/18.
 */
@Component
public class RedisConverter {

    RedisChannelEntity getRedisChannelEntity(JFMSSendMessage jfmsSendMessage){
        if (jfmsSendMessage == null)
            return null;
        RedisChannelEntity redisChannelEntity = new RedisChannelEntity(
                jfmsSendMessage.getFrom(),
                jfmsSendMessage.getTo(),
                jfmsSendMessage.getBody(),
                jfmsSendMessage.getSubject()
        );
        return redisChannelEntity;
    }

    public JFMSReceiveMessage getJFMSReceiveMessage(RedisChannelEntity redisChannelEntity) {
        if (redisChannelEntity == null)
            return null;
        JFMSReceiveMessage jfmsReceiveMessage = new JFMSReceiveMessage(
                redisChannelEntity.getId(),
                redisChannelEntity.getFrom(),
                redisChannelEntity.getMessage(),
                redisChannelEntity.getSubject()
        );
        return jfmsReceiveMessage;
    }
}
