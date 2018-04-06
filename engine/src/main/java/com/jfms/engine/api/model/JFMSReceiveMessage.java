package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/4/18.
 */
public class JFMSReceiveMessage {
    private String from;
    private String body;
    private String subject;


    public JFMSReceiveMessage(String from, String body, String subject) {
        this.from = from;
        this.body = body;
        this.subject = subject;
    }

    public JFMSReceiveMessage() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
