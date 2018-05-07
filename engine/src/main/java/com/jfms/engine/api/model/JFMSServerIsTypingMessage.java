package com.jfms.engine.api.model;

import com.jfms.engine.api.Method;

/**
 * Created by vahid on 4/10/18.
 */
public class JFMSServerIsTypingMessage {
    private Integer method;
    private String from;

    public JFMSServerIsTypingMessage(Integer method, String from) {
        this.method = method;
        this.from = from;
    }

    public JFMSServerIsTypingMessage() {
    }

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
