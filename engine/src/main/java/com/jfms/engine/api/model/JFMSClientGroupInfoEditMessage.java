package com.jfms.engine.api.model;

import java.util.Map;

public class JFMSClientGroupInfoEditMessage {
    private Integer method;
    private String id;
    private String displayName;
    private String creator;
    private Map<String, Boolean> jfmsGroupMemberMap;
    private Integer type;

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
