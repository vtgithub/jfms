package com.jfms.aaa.dal.repository;

import com.jfms.aaa.dal.entity.GroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<GroupEntity, String>, CustomGroupRepository{
    Optional<GroupEntity> findByIdAndStatus(String id, Integer status);

}
