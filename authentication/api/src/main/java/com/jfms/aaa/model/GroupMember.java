package com.jfms.aaa.model;


public class GroupMember {
    private String userName;
    private Boolean isAdmin;

    public GroupMember(String userName, Boolean isAdmin) {
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

    public GroupMember() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
