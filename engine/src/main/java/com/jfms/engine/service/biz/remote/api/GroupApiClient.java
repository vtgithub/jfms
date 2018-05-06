package com.jfms.engine.service.biz.remote.api;

import com.jfms.aaa.GroupApi;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.aaa.model.GroupMember;
import com.jfms.engine.api.model.JFMSClientGroupCreationMessage;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@FeignClient(name = "group-api", url = "localhost:6070")
public interface GroupApiClient extends GroupApi{

    static GroupInfo getGroupInfo(String displayName, String owner, Map<String, Boolean> memberMap, Integer type){

        List<GroupMember> groupMemberList = new ArrayList<>();
        Iterator<Map.Entry<String, Boolean>> memberMapIterator = memberMap.entrySet().iterator();
        while (memberMapIterator.hasNext()){
            Map.Entry<String, Boolean> member = memberMapIterator.next();
            groupMemberList.add(new GroupMember(member.getKey(), member.getValue()));
        }
        return new GroupInfo(displayName, owner, groupMemberList, type);
    }

}
