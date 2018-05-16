package com.jfms.engine.service.biz.remote.api.message_history;

import com.jfms.message_history.model.HistoryMessage;
import feign.hystrix.FallbackFactory;

import java.util.List;

public class MessageHistoryGroupApiClientFallback implements FallbackFactory<MessageHistoryGroupApiClient>{
    @Override
    public MessageHistoryGroupApiClient create(Throwable throwable) {
        return new MessageHistoryGroupApiClient() {
            @Override
            public void saveGroupHistoryMessage(String s, HistoryMessage historyMessage) {
                // todo log
                System.out.println("???????????????????? saveGroupHistoryMessage failed");
            }

            @Override
            public void updateGroupHistoryMessage(String s, HistoryMessage historyMessage) {
                // todo log
                System.out.println("???????????????????? updateGroupHistoryMessage failed");
            }

            @Override
            public List<HistoryMessage> getGroupMessages(String s, Integer integer, Integer integer1) {
                // todo log
                System.out.println("???????????????????? getGroupMessages failed");
                return null;
            }

            @Override
            public void deleteGroupMessage(String s, List<String> list) {
                // todo log
                System.out.println("???????????????????? deleteGroupMessages failed");
            }
        };
    }
}
