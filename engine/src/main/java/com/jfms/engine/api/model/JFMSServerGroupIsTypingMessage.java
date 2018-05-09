package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/10/18.
 */
public class JFMSServerGroupIsTypingMessage {
    private Integer method;
    private String groupId;
    private String from;

    public JFMSServerGroupIsTypingMessage(Integer method, String groupId, String from) {
        this.method = method;
        this.groupId = groupId;
        this.from = from;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public JFMSServerGroupIsTypingMessage() {
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
}
