package com.jfms.message_history.service;

import com.jfms.message_history.converter.MessageConverter;
import com.jfms.message_history.dal.DalAssistant;
import com.jfms.message_history.dal.EntityStatus;
import com.jfms.message_history.dal.dao.ChannelDao;
import com.jfms.message_history.dal.dao.ChannelUpdateDao;
import com.jfms.message_history.dal.entity.ChannelEntity;
import com.jfms.message_history.dal.entity.ChannelUpdateEntity;
import com.jfms.message_history.dal.entity.GroupEntity;
import com.jfms.message_history.model.HistoryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private DalAssistant dalAssistant;
    @Autowired
    private ChannelUpdateDao channelUpdateDao;


    public void saveMessage(String channelId, HistoryMessage messageForHistory) {
        ChannelEntity channelEntity = messageConverter.historyMessageToChannelEntity(channelId, messageForHistory);
        channelDao.save(channelEntity);
    }

    public void editMessage(String channelId, HistoryMessage messageForUpdate) {
        ChannelEntity previousChannelEntity = channelDao.findByChannelIdAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                channelId, messageForUpdate.getMessageId(), 1, 2);
        if (previousChannelEntity.getStatus() == EntityStatus.DELETED.getValue())
            return;
        String previousValue = previousChannelEntity.getBody();
        messageConverter.historyMessageToUpdatedChannelEntity(messageForUpdate, previousChannelEntity);
        channelDao.save(previousChannelEntity);
        ChannelUpdateEntity channelUpdateEntity = dalAssistant.getChannelUpdateEntityFromChannelEntity(
                previousValue,
                messageForUpdate.getTime(),
                previousChannelEntity
        );
        channelUpdateDao.save(channelUpdateEntity);
    }

    public List<HistoryMessage> getChannelMessages(String channelId, Integer pageSize, Integer pageNumber) {

        List<ChannelEntity> channelEntityList = channelDao.findByChannelIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                channelId,
                1,
                2
                //                ,PageRequest.of(
//                        historyMessageRequest.getPageNumber(),
//                        historyMessageRequest.getPageSize())
                );
        Collections.sort(channelEntityList);
        List<HistoryMessage> historyMessageList = messageConverter.channelEntityListToHistoryMessageList(channelEntityList);
        return historyMessageList;
    }

    public void deleteMessages(String channelId, List<String> messageIdList) {
        for (String messageId : messageIdList) {
            ChannelEntity channelEntity = channelDao.findByChannelIdAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                    channelId.trim(), messageId.trim(), 1, 2);
            if (channelEntity != null){
                channelEntity.setStatus(EntityStatus.DELETED.getValue());
                channelDao.save(channelEntity);
            }
        }
    }
}
