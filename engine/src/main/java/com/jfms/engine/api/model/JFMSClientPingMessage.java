package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/10/18.
 */
public class JFMSClientPingMessage {
    private Integer method;
    private String from;

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
