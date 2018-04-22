package com.jfms.engine.api.model;

import java.util.List;

public class JFMSClientSeenMessage {
    private String method;
    private List<String> messageIdList;
    private String from;
    private String to;
    private Long seenTime;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<String> getMessageIdList() {
        return messageIdList;
    }

    public void setMessageIdList(List<String> messageIdList) {
        this.messageIdList = messageIdList;
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

    public Long getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(Long seenTime) {
        this.seenTime = seenTime;
    }
}
