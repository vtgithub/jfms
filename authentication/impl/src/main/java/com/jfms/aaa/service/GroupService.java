package com.jfms.aaa.service;

import com.jfms.aaa.converter.GroupConverter;
import com.jfms.aaa.dal.EntityStatus;
import com.jfms.aaa.dal.entity.GroupEntity;
import com.jfms.aaa.dal.repository.NotFoundException;
import com.jfms.aaa.dal.repository.GroupRepository;
import com.jfms.aaa.model.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;


@Service
public class GroupService {

    @Autowired
    private GroupConverter groupConverter;
    @Autowired
    private GroupRepository groupRepository;

    public  String addGroup( GroupInfo groupInfo) {
        GroupEntity groupEntity = groupConverter.getEntity(EntityStatus.ACTIVE.value(), groupInfo);
        groupRepository.save(groupEntity);
        return groupEntity.getId().toString();
    }


    public  void editGroup( GroupInfo groupInfo) throws NotFoundException {
        Optional<GroupEntity> groupEntityOptional = groupRepository.findById(groupInfo.getId());
        if (groupEntityOptional.isPresent() == false || groupEntityOptional.get().getStatus() == EntityStatus.INACTIVE.value())
            throw new NotFoundException();
        GroupEntity groupEntity = groupEntityOptional.get();
        groupConverter.updateEntityByInfo(groupEntity, groupInfo);
//        groupRepository.save(groupEntity);
        groupRepository.update(groupEntity);
    }

    public  GroupInfo getGroupInfo( String groupId) throws NotFoundException {
        Optional<GroupEntity> groupEntityOptional = groupRepository.findByIdAndStatus(groupId, EntityStatus.ACTIVE.value());
        if (groupEntityOptional.isPresent() == false)
            throw new NotFoundException();
        GroupInfo groupInfo = groupConverter.getInfo(groupEntityOptional.get());
        return groupInfo;
    }


    public  void deleteGroup( String groupId) throws NotFoundException {
        Optional<GroupEntity> groupEntityOptional = groupRepository.findById(groupId);
        if (groupEntityOptional.isPresent() == false)
            throw new NotFoundException();
        GroupEntity groupEntity = groupEntityOptional.get();
        groupEntity.setStatus(EntityStatus.INACTIVE.value());
        groupRepository.update(groupEntity);

    }
}
