package com.jfms.message_history.dal.dao;

import com.jfms.message_history.dal.entity.ChannelEntity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChannelDao extends CrudRepository<ChannelEntity, UUID> {
    @Query(allowFiltering = true)
    ChannelEntity findByChannelIdAndMessageIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
            String channelId, String messageId, Integer statusGreater, Integer statusLess);

    @Query(allowFiltering = true)
    List<ChannelEntity> findByChannelIdAndStatusGreaterThanEqualAndStatusLessThanEqual(
            String channelId, Integer statusGreater, Integer statusLess);
}
