package com.jfms.engine.api.model;

import com.jfms.engine.api.Method;

public class JFMSServerConversationMessage {
    private Integer method;
    private String from;
    private Long leaveTime;

    public JFMSServerConversationMessage(Integer conversationType, String from, Long leaveTime) {
        this.method = conversationType;
        this.from = from;
        this.leaveTime = leaveTime;
    }

    public JFMSServerConversationMessage() {
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
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
