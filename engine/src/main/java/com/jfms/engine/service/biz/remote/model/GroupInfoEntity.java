package com.jfms.engine.service.biz.remote.model;

import java.io.Serializable;
import java.util.Map;

public class GroupInfoEntity implements Serializable{

    private String displayName;
    private String creator;
    private Map<String, Boolean> jfmsGroupMemberMap;
    private Integer type;

    public GroupInfoEntity(String displayName, String creator, Map<String, Boolean> jfmsGroupMemberMap, Integer type) {
        this.displayName = displayName;
        this.creator = creator;
        this.jfmsGroupMemberMap = jfmsGroupMemberMap;
        this.type = type;
    }

    public GroupInfoEntity() {
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
