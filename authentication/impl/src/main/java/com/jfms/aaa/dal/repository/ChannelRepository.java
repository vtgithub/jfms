package com.jfms.aaa.dal.repository;

import com.jfms.aaa.dal.entity.ChannelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends CrudRepository<ChannelEntity, String>, CustomChannelRepository{
    Optional<ChannelEntity> findByIdAndStatus(String id, Integer status);

}
