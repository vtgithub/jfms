package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/9/18.
 */
public class JFMSClientDeleteMessage {
    private Integer method;
    private  String id;

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
}
