package com.jfms.engine.api.model;

import java.util.UUID;

/**
 * Created by vahid on 4/4/18.
 */
public class JFMSServerSendMessage {
    private String id;
    private String from;
    private String body;
    private String subject;
    private Long sendTime;


    public JFMSServerSendMessage(String id, String from, String body, String subject, Long sendTime) {
        this.id = id;
        this.from = from;
        this.body = body;
        this.subject = subject;
        this.sendTime = sendTime;
    }

    public JFMSServerSendMessage() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }


}
