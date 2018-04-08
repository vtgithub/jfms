package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/4/18.
 */
public class JFMSLoginMessage {
    private Integer method;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }
}
