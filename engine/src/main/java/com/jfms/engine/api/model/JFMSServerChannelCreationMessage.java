package com.jfms.engine.api.model;

import java.util.List;
import java.util.Map;

public class JFMSServerChannelCreationMessage {
    private String groupId;
    private Integer method;
    private String displayName;
    private String creator;
    private List<String> jfmsChannelMemberList;
    private Integer type;

    public JFMSServerChannelCreationMessage(
            String groupId, Integer method, String displayName, String creator, List<String> jfmsChannelMemberList, Integer type) {
        this.groupId = groupId;
        this.method = method;
        this.displayName = displayName;
        this.creator = creator;
        this.jfmsChannelMemberList = jfmsChannelMemberList;
        this.type = type;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public JFMSServerChannelCreationMessage() {
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

    public List<String> getJfmsChannelMemberList() {
        return jfmsChannelMemberList;
    }

    public void setJfmsChannelMemberList(List<String> jfmsChannelMemberList) {
        this.jfmsChannelMemberList = jfmsChannelMemberList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
