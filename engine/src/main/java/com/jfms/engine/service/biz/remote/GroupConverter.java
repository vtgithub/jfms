package com.jfms.engine.service.biz.remote;

import com.jfms.engine.api.model.JFMSClientGroupCreationMessage;
import com.jfms.engine.api.model.JFMSClientGroupInfoEditMessage;
import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;
import org.springframework.stereotype.Component;

@Component
public class GroupConverter {
    public GroupInfoEntity  getEntityFromJFMSMessage(JFMSClientGroupCreationMessage jfmsClientGroupCreationMessage){
        return new GroupInfoEntity(
                jfmsClientGroupCreationMessage.getDisplayName(),
                jfmsClientGroupCreationMessage.getCreator(),
                jfmsClientGroupCreationMessage.getJfmsGroupMemberMap(),
                jfmsClientGroupCreationMessage.getType()
        );
    }

    public GroupInfoEntity  getEntityFromJFMSMessage(JFMSClientGroupInfoEditMessage jfmsClientGroupInfoEditMessage){
        return new GroupInfoEntity(
                jfmsClientGroupInfoEditMessage.getDisplayName(),
                jfmsClientGroupInfoEditMessage.getCreator(),
                jfmsClientGroupInfoEditMessage.getJfmsGroupMemberMap(),
                jfmsClientGroupInfoEditMessage.getType()
        );
    }
}
