package com.jfms.aaa.model;

import java.util.List;

public class GroupInfo {
    private String id;
    private String displayName;
    private String owner;
    private List<GroupMember> memberList;
    private Integer type;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<GroupMember> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<GroupMember> memberList) {
        this.memberList = memberList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
