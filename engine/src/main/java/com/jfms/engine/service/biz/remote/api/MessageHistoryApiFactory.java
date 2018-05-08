package com.jfms.engine.service.biz.remote.api;

import com.jfms.engine.service.biz.remote.api.message_history.MessageHistoryGroupApiClient;
import com.jfms.engine.service.biz.remote.api.message_history.MessageHistoryP2PApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHistoryApiFactory {
    @Autowired
    private MessageHistoryP2PApiClient messageHistoryP2PApiClient;
    @Autowired
    private MessageHistoryGroupApiClient messageHistoryGroupApiClient;

    public MessageHistoryP2PApiClient getP2PApi(){
        return this.messageHistoryP2PApiClient;
    }

    public MessageHistoryGroupApiClient getGroupApi() {
        return this.messageHistoryGroupApiClient;
    }
}
