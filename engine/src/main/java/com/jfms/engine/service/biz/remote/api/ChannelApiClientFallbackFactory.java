package com.jfms.engine.service.biz.remote.api;

import com.jfms.aaa.model.ChannelInfo;
import com.jfms.aaa.model.GroupInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ChannelApiClientFallbackFactory implements FallbackFactory<ChannelApiClient>{
    @Override
    public ChannelApiClient create(Throwable throwable) {
        return new ChannelApiClient() {
            @Override
            public String addChannel(ChannelInfo channelInfo) {
                //todo log
                System.out.println("?????????????????????? addChannel failed");
                return null;
            }

            @Override
            public void editChannel(ChannelInfo channelInfo) {
                //todo log
                System.out.println("?????????????????????? editChannel failed");
            }

            @Override
            public ChannelInfo getChannel(String s) {
                //todo log
                System.out.println("?????????????????????? getChannel failed");
                return null;
            }

            @Override
            public void deleteChannel(String s) {
                //todo log
                System.out.println("?????????????????????? deleteChannel failed");
            }
        };
    }
}
