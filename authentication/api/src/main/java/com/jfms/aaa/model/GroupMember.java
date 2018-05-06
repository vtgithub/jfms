package com.jfms.aaa.model;


public class GroupMember {
    private String userName;
    private Boolean admin;

    public GroupMember(String userName, Boolean admin) {
        this.userName = userName;
        this.admin = admin;
    }

    public GroupMember() {
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
