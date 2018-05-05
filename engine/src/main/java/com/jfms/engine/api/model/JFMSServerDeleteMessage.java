package com.jfms.engine.api.model;

import com.jfms.engine.api.Method;

/**
 * Created by vahid on 4/9/18.
 */
public class JFMSServerDeleteMessage {
    private Integer method;
    private String id;
    private String from;

    public JFMSServerDeleteMessage(String id, String from) {
        this.method = Method.DELETE.getValue();
        this.id = id;
        this.from = from;
    }

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
