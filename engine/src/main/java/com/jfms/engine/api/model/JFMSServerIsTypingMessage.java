package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/10/18.
 */
public class JFMSServerIsTypingMessage {
    private String from;

    public JFMSServerIsTypingMessage(String from) {
        this.from = from;
    }

    public JFMSServerIsTypingMessage() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
