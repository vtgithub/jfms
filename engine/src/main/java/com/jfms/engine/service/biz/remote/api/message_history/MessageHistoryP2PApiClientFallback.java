package com.jfms.engine.service.biz.remote.api.message_history;

import com.jfms.message_history.model.HistoryMessage;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MessageHistoryP2PApiClientFallback implements FallbackFactory<MessageHistoryP2PApiClient>{
    @Override
    public MessageHistoryP2PApiClient create(Throwable throwable) {
        return new MessageHistoryP2PApiClient() {
            @Override
            public void saveP2PHistoryMessage(String s, HistoryMessage historyMessage) {
                // todo log
                System.out.println("????????????????? saveP2PHistoryMessage failed");
            }

            @Override
            public void updateP2PHistoryMessage(String s, HistoryMessage historyMessage) {
                // todo log
                System.out.println("????????????????? updateP2PHistoryMessage failed");
            }

            @Override
            public List<HistoryMessage> getUserP2PMessages(String s, String s1, Integer integer, Integer integer1) {
                // todo log
                System.out.println("????????????????? getUserP2PMessages failed");
                return null;
            }

            @Override
            public void deleteP2PMessage(String s, List<String> list) {
                // todo log
                System.out.println("????????????????? deleteP2PMessage failed");
            }
        };
    }
}
