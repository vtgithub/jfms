package com.jfms.engine.service.biz.remote.api;

import com.jfms.aaa.model.GroupInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class GroupApiClientFallbackFactory implements FallbackFactory<GroupApiClient>{
    @Override
    public GroupApiClient create(Throwable throwable) {
        return new GroupApiClient() {
            @Override
            public String addGroup(GroupInfo groupInfo) {
                //todo log
                System.out.println("?????????????????????? addGroup failed");
                return null;
            }

            @Override
            public void editGroup(GroupInfo groupInfo) {
                //todo log
                System.out.println("?????????????????????? editGroup failed");
            }

            @Override
            public GroupInfo getGroup(String s) {
                //todo log
                System.out.println("?????????????????????? getGroup failed");
                return null;
            }

            @Override
            public void deleteGroup(String s) {
                //todo log
                System.out.println("?????????????????????? deleteGroup failed");
            }
        };
    }
}
