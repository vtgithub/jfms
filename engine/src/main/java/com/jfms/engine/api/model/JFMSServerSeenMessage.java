package com.jfms.engine.api.model;

import java.util.List;

public class JFMSServerSeenMessage {
    private List<String> messageIdList;
    private Long seenTime;
    private String from;

    public JFMSServerSeenMessage() {
    }

    public JFMSServerSeenMessage(List<String> messageIdList, Long seenTime, String from) {
        this.messageIdList = messageIdList;
        this.seenTime = seenTime;
        this.from = from;
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
