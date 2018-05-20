package com.jfms.engine.service.biz.remote.api;

import com.jfms.engine.service.biz.remote.model.GroupInfoEntity;

public interface ChannelRepository {
    void saveChannelInfo(String channelId, GroupInfoEntity channelInfoEntity);
    GroupInfoEntity getChannelInfo(String channelId);
}
