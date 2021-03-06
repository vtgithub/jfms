package com.jfms.message_history.dal.dao;

import com.jfms.message_history.dal.entity.P2PUpdateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface P2PUpdateDao extends CrudRepository<P2PUpdateEntity, UUID> {
}
