package com.jfms.message_history.model;

public class P2PMessage {
    private String messageId;
    private String sender;
    private String body;
    private String subject;
    private Long time;

    public P2PMessage(String messageId, String sender, String body, String subject, Long time) {
        this.messageId = messageId;
        this.sender = sender;
        this.body = body;
        this.subject = subject;
        this.time = time;
    }

    public P2PMessage() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
