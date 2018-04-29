package com.jfms.engine.service.biz.remote.model;

import java.util.UUID;

/**
 * Created by vahid on 4/4/18.
 */
public class OnlineMessageEntity {
    private String id;
    private String from;
    private String to;
    private String message;
    private String subject;
    private Long sendTime;


    public OnlineMessageEntity(String id, String from, String to, String message, String subject, Long sendTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.subject = subject;
        this.sendTime = sendTime;
    }

    public OnlineMessageEntity() {
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
