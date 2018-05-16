package com.jfms.engine.service.biz.remote.api;


import com.jfms.aaa.GroupApi;
import com.jfms.aaa.model.GroupInfo;
import com.jfms.aaa.model.GroupMember;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//url = "localhost:6070"
//serviceId = "aaa-server"
@FeignClient(name = "group-api", serviceId = "aaa-server", fallbackFactory = GroupApiClientFallbackFactory.class)
public interface GroupApiClient extends GroupApi{

    static GroupInfo getGroupInfo(
            String displayName, String creator, Map<String, Boolean> jfmsGroupMemberMap, Integer type) {
        List<GroupMember> groupMembers = new ArrayList<>();
        Iterator<Map.Entry<String, Boolean>> memberIterator = jfmsGroupMemberMap.entrySet().iterator();
        while (memberIterator.hasNext()){
            Map.Entry<String, Boolean> next = memberIterator.next();
            groupMembers.add(new GroupMember(next.getKey(),next.getValue()));
        }
        return new GroupInfo(
                displayName,
                creator,
                groupMembers,
                type
        );
    }

    static GroupInfo getGroupInfo(
            String id, String displayName, String creator, Map<String, Boolean> jfmsGroupMemberMap, Integer type) {
        GroupInfo groupInfo = getGroupInfo(displayName, creator, jfmsGroupMemberMap, type);
        groupInfo.setId(id);
        return groupInfo;
    }


}
