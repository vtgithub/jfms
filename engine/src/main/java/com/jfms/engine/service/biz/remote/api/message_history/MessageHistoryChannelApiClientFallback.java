package com.jfms.engine.service.biz.remote.api.message_history;

import com.jfms.message_history.model.HistoryMessage;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageHistoryChannelApiClientFallback implements FallbackFactory<MessageHistoryChannelApiClient>{
    @Override
    public MessageHistoryChannelApiClient create(Throwable throwable) {
        return new MessageHistoryChannelApiClient() {
            @Override
            public void saveChannelHistoryMessage(String s, HistoryMessage historyMessage) {
                // todo log
                System.out.println("???????????????????? saveChannelHistoryMessage failed");
            }

            @Override
            public void updateChannelHistoryMessage(String s, HistoryMessage historyMessage) {
                // todo log
                System.out.println("???????????????????? updateChannelHistoryMessage failed");
            }

            @Override
            public List<HistoryMessage> getChannelMessages(String s, Integer integer, Integer integer1) {
                // todo log
                System.out.println("???????????????????? getChannelMessages failed");
                return null;
            }

            @Override
            public void deleteChannelMessage(String s, List<String> list) {
                // todo log
                System.out.println("???????????????????? deleteChannelMessages failed");
            }
        };
    }
}
