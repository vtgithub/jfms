package com.jfms.aaa.service;

import com.jfms.aaa.converter.ChannelConverter;
import com.jfms.aaa.dal.EntityStatus;
import com.jfms.aaa.dal.entity.ChannelEntity;
import com.jfms.aaa.dal.repository.ChannelRepository;
import com.jfms.aaa.dal.repository.NotFoundException;
import com.jfms.aaa.model.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ChannelService {

    @Autowired
    private ChannelConverter channelConverter;
    @Autowired
    private ChannelRepository channelRepository;

    public  String addChannel(GroupInfo channelInfo) {
        ChannelEntity channelEntity = channelConverter.getEntity(EntityStatus.ACTIVE.value(), channelInfo);
        channelRepository.save(channelEntity);
        return channelEntity.getId().toString();
    }


    public  void editChannel(GroupInfo channelInfo) throws NotFoundException {
        Optional<ChannelEntity> channelEntityOptional = channelRepository.findById(channelInfo.getId());
        if (channelEntityOptional.isPresent() == false || channelEntityOptional.get().getStatus() == EntityStatus.INACTIVE.value())
            throw new NotFoundException();
        ChannelEntity channelEntity = channelEntityOptional.get();
        channelConverter.updateEntityByInfo(channelEntity, channelInfo);
//        channelRepository.save(channelEntity);
        channelRepository.update(channelEntity);
    }

    public GroupInfo getChannelInfo(String channelId) throws NotFoundException {
        Optional<ChannelEntity> channelEntityOptional = channelRepository.findByIdAndStatus(channelId, EntityStatus.ACTIVE.value());
        if (channelEntityOptional.isPresent() == false)
            throw new NotFoundException();
        GroupInfo channelInfo = channelConverter.getInfo(channelEntityOptional.get());
        return channelInfo;
    }


    public  void deleteChannel(String channelId) throws NotFoundException {
        Optional<ChannelEntity> channelEntityOptional = channelRepository.findById(channelId);
        if (channelEntityOptional.isPresent() == false)
            throw new NotFoundException();
        ChannelEntity channelEntity = channelEntityOptional.get();
        channelEntity.setStatus(EntityStatus.INACTIVE.value());
        channelRepository.update(channelEntity);

    }
}
