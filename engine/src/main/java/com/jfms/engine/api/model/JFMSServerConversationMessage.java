package com.jfms.engine.api.model;

public class JFMSServerConversationMessage {
    private String from;
    private Long leaveTime;

    public JFMSServerConversationMessage(String from, Long leaveTime) {
        this.from = from;
        this.leaveTime = leaveTime;
    }

    public JFMSServerConversationMessage() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Long leaveTime) {
        this.leaveTime = leaveTime;
    }
}
