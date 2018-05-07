package com.jfms.message_history.dal.dao;

import com.jfms.message_history.dal.entity.GroupEntity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupDao extends CrudRepository<GroupEntity, UUID> {
    @Query(allowFiltering = true)
    GroupEntity findByGroupIdAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
            String groupId, String messageId, Integer statusGreater, Integer statusLess);

    @Query(allowFiltering = true)
    List<GroupEntity> findByGroupIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
            String groupId, Integer statusGreater, Integer statusLess);
}
