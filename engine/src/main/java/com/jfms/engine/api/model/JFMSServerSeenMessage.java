package com.jfms.engine.api.model;

import com.jfms.engine.api.Method;

import java.util.List;

public class JFMSServerSeenMessage {

    private Integer method;
    private List<String> messageIdList;
    private Long seenTime;
    private String from;

    public JFMSServerSeenMessage() {
    }

    public JFMSServerSeenMessage(List<String> messageIdList, Long seenTime, String from) {
        this.method = Method.SEEN.getValue();
        this.messageIdList = messageIdList;
        this.seenTime = seenTime;
        this.from = from;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public List<String> getMessageIdList() {
        return messageIdList;
    }

    public void setMessageIdList(List<String> messageIdList) {
        this.messageIdList = messageIdList;
    }

    public Long getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(Long seenTime) {
        this.seenTime = seenTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
