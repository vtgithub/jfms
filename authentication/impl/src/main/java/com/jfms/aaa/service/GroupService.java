package com.jfms.aaa.service;

import com.jfms.aaa.converter.GroupConverter;
import com.jfms.aaa.dal.repository.GroupRepository;
import com.jfms.aaa.model.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupConverter groupConverter;
    @Autowired
    private GroupRepository groupRepository;

    public String addGroup(GroupInfo groupInfo) {

        return null;
    }
}
