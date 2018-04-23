package com.jfms.message_history.dal.dao;

import com.jfms.message_history.dal.entity.P2PUpdateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface P2PUpdateDao extends CrudRepository<P2PUpdateEntity, UUID> {
}
