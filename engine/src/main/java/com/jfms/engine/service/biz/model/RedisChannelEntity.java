package com.jfms.engine.service.biz.model;

/**
 * Created by vahid on 4/4/18.
 */
public class RedisChannelEntity {
    private String from;
    private String to;
    private String message;
    private String subject;


    public RedisChannelEntity(String from, String to, String message, String subject) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.subject = subject;
    }

    public RedisChannelEntity() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
