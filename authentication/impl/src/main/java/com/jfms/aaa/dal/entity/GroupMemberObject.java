package com.jfms.aaa.dal.entity;

import java.io.Serializable;

public class GroupMemberObject implements Serializable{
    private String userName;
    private Boolean admin;

    public GroupMemberObject(String userName, Boolean isAdmin) {
        this.userName = userName;
        this.admin= isAdmin;
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
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
