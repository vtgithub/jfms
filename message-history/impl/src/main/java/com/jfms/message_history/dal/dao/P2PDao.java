package com.jfms.message_history.dal.dao;

import com.jfms.message_history.dal.entity.P2PEntity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface P2PDao extends CrudRepository<P2PEntity, UUID>{
//    List<P2PEntity> findByOwnerAndFrom(String userId, String rosterId, Pageable pageable);
//    P2PEntity findByOwnerAndMessageId(String owner, String messageId);
    @Query("SELECT * FROM P2PEntity where owner = ?0 AND sender = ?1 AND status <= 2  ALLOW FILTERING")
    List<P2PEntity> findByOwnerAndFromAndStatus(String owner, String from, PageRequest time);
    @Query("SELECT * FROM P2PEntity where owner = ?0 AND messageId = ?1 And status <= 2  ALLOW FILTERING")
    P2PEntity findByOwnerAndMessageIdAndStatus(String owner, String messageId);
}
