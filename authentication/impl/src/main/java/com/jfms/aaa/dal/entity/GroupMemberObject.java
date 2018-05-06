package com.jfms.aaa.dal.entity;

import java.io.Serializable;

public class GroupMemberObject implements Serializable{
    private String userName;
    private Boolean isAdmin;

    public GroupMemberObject(String userName, Boolean isAdmin) {
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

    public GroupMemberObject() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
