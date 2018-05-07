package com.jfms.message_history.service;

import com.jfms.message_history.converter.MessageConverter;
import com.jfms.message_history.dal.DalAssistant;
import com.jfms.message_history.dal.EntityStatus;
import com.jfms.message_history.dal.dao.GroupDao;
import com.jfms.message_history.dal.dao.GroupUpdateDao;
import com.jfms.message_history.dal.entity.GroupEntity;
import com.jfms.message_history.dal.entity.GroupUpdateEntity;
import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.model.HistoryMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private DalAssistant dalAssistant;
    @Autowired
    private GroupUpdateDao groupUpdateDao;
    @Autowired
    private P2PService p2PService;

    public void saveMessage(String groupId, HistoryMessage messageForHistory) {
        GroupEntity groupEntity = messageConverter.historyMessageToGroupEntity(groupId, messageForHistory);
        groupDao.save(groupEntity);
    }

    public void editMessage(String groupId, HistoryMessage messageForUpdate) {
        GroupEntity previousGroupEntity = groupDao.findByGroupIdAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                groupId, messageForUpdate.getMessageId(), 1, 2);
        if (previousGroupEntity.getStatus() == EntityStatus.DELETED.getValue())
            return;
        String previousValue = previousGroupEntity.getBody();
        messageConverter.historyMessageToUpdatedGroupEntity(messageForUpdate, previousGroupEntity);
        groupDao.save(previousGroupEntity);
        GroupUpdateEntity groupUpdateEntity = dalAssistant.getGroupUpdateEntityFromGroupEntity(
                previousValue,
                messageForUpdate.getTime(),
                previousGroupEntity
        );
        groupUpdateDao.save(groupUpdateEntity);
    }

    public List<HistoryMessage> getGroupMessages(String groupId, Integer pageSize, Integer pageNumber) {

        List<GroupEntity> groupEntityList = groupDao.findByGroupIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                groupId,
                1,
                2
                //                ,PageRequest.of(
//                        historyMessageRequest.getPageNumber(),
//                        historyMessageRequest.getPageSize())
                );
        Collections.sort(groupEntityList);
        List<HistoryMessage> historyMessageList = messageConverter.groupEntityListToHistoryMessageList(groupEntityList);
        return historyMessageList;
    }

    public void deleteMessages(String groupId, List<String> messageIdList) {
        for (String messageId : messageIdList) {
            GroupEntity groupEntity = groupDao.findByGroupIdAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                    groupId, messageId, 1, 2);
            groupEntity.setStatus(EntityStatus.DELETED.getValue());
            groupDao.save(groupEntity);
        }
    }
}
