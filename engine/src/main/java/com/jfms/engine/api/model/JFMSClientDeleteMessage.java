package com.jfms.engine.api.model;

import java.util.List;

/**
 * Created by vahid on 4/9/18.
 */
public class JFMSClientDeleteMessage {
    private Integer method;
    private String from;
    private String to;
    private List<String> idList;

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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
