package com.jfms.engine.api.model;

import java.util.List;

/**
 * Created by vahid on 4/9/18.
 */
public class JFMSServerGroupDeleteMessage {
    private Integer method;
    private String groupId;
    private List<String> idList;
    private String from;

    public JFMSServerGroupDeleteMessage(Integer method, String groupId, List<String> idList, String from) {
        this.method = method;
        this.groupId = groupId;
        this.idList = idList;
        this.from = from;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
