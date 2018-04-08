package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/4/18.
 */
public class JFMSServerEditMessage {

    private String id;
    private String from;
    private String body;
    private String subject;
    private Long editTime;


    public JFMSServerEditMessage(String id, String from, String body, String subject, Long editTime) {
        this.id = id;
        this.from = from;
        this.body = body;
        this.subject = subject;
        this.editTime = editTime;
    }

    public JFMSServerEditMessage() {
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

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }
}
