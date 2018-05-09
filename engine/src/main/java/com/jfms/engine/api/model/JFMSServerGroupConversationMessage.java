package com.jfms.engine.api.model;

public class JFMSServerGroupConversationMessage {
    private Integer method;
    private String groupId;
    private String from;
    private Long leaveTime;

    public JFMSServerGroupConversationMessage(Integer method, String groupId, String from, Long leaveTime) {
        this.method = method;
        this.groupId = groupId;
        this.from = from;
        this.leaveTime = leaveTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public JFMSServerGroupConversationMessage() {
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
