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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        P2PEntity previousP2PEntity = p2PDao.findByOwnerAndMessageIdAndStatus(userId, messageForUpdate.getMessageId());
        String previousValue = previousP2PEntity.getBody();
        P2PEntity newP2PEntity = messageConverter.p2PMessageToP2pEntity(messageForUpdate, previousP2PEntity);
        p2PDao.save(newP2PEntity);
        P2PUpdateEntity p2PUpdateEntity = dalAssistant.getP2PUpdateEntityFromP2PEntity(previousValue, newP2PEntity);
        p2PUpdateDao.save(p2PUpdateEntity);
    }

    public List<P2PMessage> getUserP2PMessages(String userId, String rosterId, P2PMessageRequest p2PMessageRequest) {
        List<P2PEntity> p2PEntityList = p2PDao.findByOwnerAndFromAndStatus(
                userId,
                rosterId,
                PageRequest.of(
                        p2PMessageRequest.getPageNumber(),
                        p2PMessageRequest.getPageSize(),
                        Sort.Direction.DESC,"time")
        );
        return messageConverter.p2PEntityListToP2PMessageList(p2PEntityList);
    }

    public void deleteP2PMessage(String userId, List<String> messageIdList) {
        for (String messageId : messageIdList) {
            P2PEntity p2PEntity= p2PDao.findByOwnerAndMessageIdAndStatus(userId, messageId);
            p2PEntity.setStatus(EntityStatus.DELETED.getValue());
            p2PDao.save(p2PEntity);
        }
    }
}
