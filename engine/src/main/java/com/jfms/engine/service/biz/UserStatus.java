package com.jfms.engine.service.biz;

public enum UserStatus {
    ONLINE("online"),
    OFFLINE("offline");

    private String status;

    public String getValue() {
        return status;
    }

    public void setValue(String value) {
        this.status = value;
    }

    UserStatus(String value) {
        this.status= value;
    }
}
