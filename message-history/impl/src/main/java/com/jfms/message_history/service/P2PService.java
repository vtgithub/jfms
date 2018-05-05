package com.jfms.message_history.service;

import com.jfms.message_history.converter.MessageConverter;
import com.jfms.message_history.dal.DalAssistant;
import com.jfms.message_history.dal.EntityStatus;
import com.jfms.message_history.dal.dao.P2PDao;
import com.jfms.message_history.dal.dao.P2PUpdateDao;
import com.jfms.message_history.dal.entity.P2PEntity;
import com.jfms.message_history.dal.entity.P2PUpdateEntity;
import com.jfms.message_history.model.P2PMessage;
import com.jfms.message_history.model.P2PMessageRequest;
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

    public void saveMessage(String userId, P2PMessage messageForHistory) {
        P2PEntity p2PEntity = messageConverter.p2PMessageToP2pEntity(userId, messageForHistory);
        p2PDao.save(p2PEntity);
    }

    public void editMessage(String userId, P2PMessage messageForUpdate) {
        P2PEntity previousP2PEntity = p2PDao.findByOwnerAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
                userId, messageForUpdate.getMessageId(), 1, 2);
        if (previousP2PEntity.getStatus() == EntityStatus.DELETED.getValue())
            return;
        messageConverter.p2PMessageToP2pEntity(messageForUpdate, previousP2PEntity);
        p2PDao.save(previousP2PEntity);

        String previousValue = previousP2PEntity.getBody();
        P2PUpdateEntity p2PUpdateEntity = dalAssistant.getP2PUpdateEntityFromP2PEntity(
                previousValue,
                messageForUpdate.getTime(),
                previousP2PEntity
        );
        p2PUpdateDao.save(p2PUpdateEntity);
    }

    public List<P2PMessage> getUserP2PMessages(String userId, String rosterId, P2PMessageRequest p2PMessageRequest) {
//        List<P2PEntity> p2PEntityList = p2PDao.findByOwnerAndSenderAndStatus(
//                userId,
//                rosterId,
//                PageRequest.of(
//                        p2PMessageRequest.getPageNumber(),
//                        p2PMessageRequest.getPageSize(),
//                        Sort.Direction.DESC,"time")
//        );

        List<P2PEntity> p2PEntityList =
                p2PDao.findByOwnerAndSenderAndStatusGreaterThanEqualAndStatusLessThanEqual(
                userId,
                rosterId,
                1,
                2
//                PageRequest.of(
//                        p2PMessageRequest.getPageNumber(),
//                        p2PMessageRequest.getPageSize())
        );
        Collections.sort(p2PEntityList);
        List<P2PMessage> sendP2PMessageList = messageConverter.p2PEntityListToP2PMessageList(p2PEntityList);
        p2PEntityList =
                p2PDao.findByOwnerAndSenderAndStatusGreaterThanEqualAndStatusLessThanEqual(
                        rosterId,
                        userId,
                        1,
                        2
//                PageRequest.of(
//                        p2PMessageRequest.getPageNumber(),
//                        p2PMessageRequest.getPageSize())
                );
        Collections.sort(p2PEntityList);
        List<P2PMessage> replayP2PMessageList = messageConverter.p2PEntityListToP2PMessageList(p2PEntityList);

        return merge(sendP2PMessageList, replayP2PMessageList);
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

    private List<P2PMessage> merge(List<P2PMessage> sendP2PMessageList, List<P2PMessage> replayP2PMessageList) {
        List<P2PMessage> conversation = new ArrayList<P2PMessage>();
        if (sendP2PMessageList == null && replayP2PMessageList == null)
            return null;
        if (sendP2PMessageList == null)
            return replayP2PMessageList;
        if (replayP2PMessageList == null)
            return sendP2PMessageList;
        int i = 0;
        int j = 0;
        while (i != sendP2PMessageList.size() && j!=replayP2PMessageList.size()){
            if (sendP2PMessageList.get(i).getTime() < replayP2PMessageList.get(j).getTime()){
                conversation.add(sendP2PMessageList.get(i));
                i++;
            }
            else{
                conversation.add(replayP2PMessageList.get(j));
                j++;
            }
        }
        if (i == sendP2PMessageList.size() && j < replayP2PMessageList.size())
            conversation.addAll(replayP2PMessageList.subList(j, replayP2PMessageList.size()));
        else if (j == replayP2PMessageList.size() && i < sendP2PMessageList.size() )
            conversation.addAll(sendP2PMessageList.subList(i, sendP2PMessageList.size()));
        return conversation;
    }
}
