package com.jfms.engine.api.model;

public class JFMSClientConversationLeaveMessage {
    private Integer method;
    private String from;
    private String to;
    private Long leaveTime;

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

    public Long getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Long leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }
}
