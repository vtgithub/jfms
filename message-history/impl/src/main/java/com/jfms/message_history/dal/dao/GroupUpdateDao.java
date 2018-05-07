package com.jfms.message_history.dal.dao;

import com.jfms.message_history.dal.entity.GroupUpdateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupUpdateDao extends CrudRepository<GroupUpdateEntity, UUID>{
}
