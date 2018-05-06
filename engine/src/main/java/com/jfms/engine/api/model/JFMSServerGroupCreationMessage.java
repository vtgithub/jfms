package com.jfms.engine.api.model;

import java.util.Map;

public class JFMSServerGroupCreationMessage {
    private String groupId;
    private Integer method;
    private String displayName;
    private String creator;
    private Map<String, Boolean> jfmsGroupMemberMap;
    private Integer type;

    public JFMSServerGroupCreationMessage(
            String groupId, Integer method, String displayName, String creator, Map<String, Boolean> jfmsGroupMemberMap, Integer type) {
        this.groupId = groupId;
        this.method = method;
        this.displayName = displayName;
        this.creator = creator;
        this.jfmsGroupMemberMap = jfmsGroupMemberMap;
        this.type = type;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public JFMSServerGroupCreationMessage() {
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Map<String, Boolean> getJfmsGroupMemberMap() {
        return jfmsGroupMemberMap;
    }

    public void setJfmsGroupMemberMap(Map<String, Boolean> jfmsGroupMemberMap) {
        this.jfmsGroupMemberMap = jfmsGroupMemberMap;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
