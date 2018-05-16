package com.jfms.engine.service.biz.remote.api;

import com.jfms.offline_message.OfflineMessageApi;
import com.jfms.offline_message.model.OfflineMessage;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfflineMessageApiClientFallbackFactory implements FallbackFactory<OfflineMessageApiClient>{
    @Override
    public OfflineMessageApiClient create(Throwable throwable) {
        return new OfflineMessageApiClient() {
            @Override
            public void produceMessage(OfflineMessage offlineMessage) {
                System.out.println("???????????????????????????? produce failed");
                //todo log
            }

            @Override
            public List<String> consumeMessage(String s) {
                System.out.println("???????????????????????????? consume failed");
                //todo log
                return null;
            }
        };
    }
}
