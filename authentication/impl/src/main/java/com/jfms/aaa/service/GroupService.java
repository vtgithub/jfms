package com.jfms.aaa.service;

import com.jfms.aaa.converter.GroupConverter;
import com.jfms.aaa.dal.entity.GroupEntity;
import com.jfms.aaa.dal.repository.DatabaseException;
import com.jfms.aaa.dal.repository.GroupRepository;
import com.jfms.aaa.model.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class GroupService {

    @Autowired
    private GroupConverter groupConverter;
    @Autowired
    private GroupRepository groupRepository;

    public String addGroup(GroupInfo groupInfo) {
        GroupEntity groupEntity = groupConverter.getEntity(groupInfo);
        groupRepository.save(groupEntity);
        return groupEntity.getId().toString();
    }


    public void editGroup(GroupInfo groupInfo) throws DatabaseException {
        Optional<GroupEntity> groupEntityOptional = groupRepository.findById(groupInfo.getId());
        if (groupEntityOptional.isPresent() == false)
            throw new DatabaseException();
        GroupEntity groupEntity = groupEntityOptional.get();
        groupConverter.updateEntityByInfo(groupEntity, groupInfo);
        groupRepository.save(groupEntity);
        //todo save changes as list
    }

    public GroupInfo getGroupInfo(String groupId) throws DatabaseException {
        Optional<GroupEntity> groupEntityOptional = groupRepository.findById(groupId);
        if (groupEntityOptional.isPresent() == false)
            throw new DatabaseException();
        GroupInfo groupInfo = groupConverter.getInfo(groupEntityOptional.get());
        return groupInfo;
    }


}
