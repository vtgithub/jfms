package com.jfms.message_history.service;

import com.jfms.message_history.converter.MessageConverter;
import com.jfms.message_history.dal.DalAssistant;
import com.jfms.message_history.dal.EntityStatus;
import com.jfms.message_history.dal.dao.P2PDao;
import com.jfms.message_history.dal.dao.P2PUpdateDao;
import com.jfms.message_history.dal.entity.P2PEntity;
import com.jfms.message_history.dal.entity.P2PUpdateEntity;
import com.jfms.message_history.model.HistoryMessage;
import com.jfms.message_history.model.HistoryMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class P2PService {

    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private P2PDao p2PDao;
    @Autowired
    private DalAssistant dalAssistant;
    @Autowired
    private P2PUpdateDao p2PUpdateDao;

    public void saveMessage(String userId, HistoryMessage messageForHistory) {
        P2PEntity p2PEntity = messageConverter.historyMessageToP2pEntity(userId, messageForHistory);
        p2PDao.save(p2PEntity);
    }

    public void editMessage(String userId, HistoryMessage messageForUpdate) {
        P2PEntity previousP2PEntity = p2PDao.findByOwnerAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                userId, messageForUpdate.getMessageId(), 1, 2);
        if (previousP2PEntity.getStatus() == EntityStatus.DELETED.getValue())
            return;
        String previousValue = previousP2PEntity.getBody();
        messageConverter.historyMessageToUpdatedP2pEntity(messageForUpdate, previousP2PEntity);
        p2PDao.save(previousP2PEntity);

        P2PUpdateEntity p2PUpdateEntity = dalAssistant.getP2PUpdateEntityFromP2PEntity(
                previousValue,
                messageForUpdate.getTime(),
                previousP2PEntity
        );
        p2PUpdateDao.save(p2PUpdateEntity);
    }

    public List<HistoryMessage> getUserP2PMessages(String userId, String rosterId, Integer pageSize, Integer pageNumber) {
//        List<P2PEntity> p2PEntityList = p2PDao.findByOwnerAndSenderAndStatus(
//                userId,
//                rosterId,
//                PageRequest.of(
//                        historyMessageRequest.getPageNumber(),
//                        historyMessageRequest.getPageSize(),
//                        Sort.Direction.DESC,"time")
//        );

        List<P2PEntity> p2PEntityList =
                p2PDao.findByOwnerAndSenderAndStatusGreaterThanEqualAndStatusLessThanEqual(
                userId,
                rosterId,
                1,
                2
//                PageRequest.of(
//                        historyMessageRequest.getPageNumber(),
//                        historyMessageRequest.getPageSize())
        );
        Collections.sort(p2PEntityList);
        List<HistoryMessage> sendHistoryMessageList = messageConverter.p2PEntityListToHistoryMessageList(p2PEntityList);
        p2PEntityList =
                p2PDao.findByOwnerAndSenderAndStatusGreaterThanEqualAndStatusLessThanEqual(
                        rosterId,
                        userId,
                        1,
                        2
//                PageRequest.of(
//                        historyMessageRequest.getPageNumber(),
//                        historyMessageRequest.getPageSize())
                );
        Collections.sort(p2PEntityList);
        List<HistoryMessage> replayHistoryMessageList = messageConverter.p2PEntityListToHistoryMessageList(p2PEntityList);

        return merge(sendHistoryMessageList, replayHistoryMessageList);
    }

    public void deleteP2PMessage(String userId, List<String> messageIdList) {
        for (String messageId : messageIdList) {
            P2PEntity p2PEntity= p2PDao.findByOwnerAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                    userId, messageId, 1, 2);
            p2PEntity.setStatus(EntityStatus.DELETED.getValue());
            p2PDao.save(p2PEntity);
        }
    }

    //------ helper

    public List<HistoryMessage> merge(List<HistoryMessage> sendHistoryMessageList, List<HistoryMessage> replayHistoryMessageList) {
        List<HistoryMessage> conversation = new ArrayList<HistoryMessage>();
        if (sendHistoryMessageList == null && replayHistoryMessageList == null)
            return null;
        if (sendHistoryMessageList == null)
            return replayHistoryMessageList;
        if (replayHistoryMessageList == null)
            return sendHistoryMessageList;
        int i = 0;
        int j = 0;
        while (i != sendHistoryMessageList.size() && j!= replayHistoryMessageList.size()){
            if (sendHistoryMessageList.get(i).getTime() < replayHistoryMessageList.get(j).getTime()){
                conversation.add(sendHistoryMessageList.get(i));
                i++;
            }
            else{
                conversation.add(replayHistoryMessageList.get(j));
                j++;
            }
        }
        if (i == sendHistoryMessageList.size() && j < replayHistoryMessageList.size())
            conversation.addAll(replayHistoryMessageList.subList(j, replayHistoryMessageList.size()));
        else if (j == replayHistoryMessageList.size() && i < sendHistoryMessageList.size() )
            conversation.addAll(sendHistoryMessageList.subList(i, sendHistoryMessageList.size()));
        return conversation;
    }
}
