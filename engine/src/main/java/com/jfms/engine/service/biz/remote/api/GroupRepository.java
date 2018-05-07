package com.jfms.engine.service.biz.remote.api;

import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;

public interface GroupRepository {
    void saveGroupInfo(String groupId, GroupInfoEntity groupInfoEntity);
    GroupInfoEntity getGroupInfo(String groupId);
}
