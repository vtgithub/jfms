package com.jfms.engine.api.model;

import com.jfms.engine.api.Method;

import java.util.List;

/**
 * Created by vahid on 4/9/18.
 */
public class JFMSServerDeleteMessage {
    private Integer method;
    private List<String> idList;
    private String from;

    public JFMSServerDeleteMessage(Integer method, List<String> idList, String from) {
        this.method = method;
        this.idList = idList;
        this.from = from;
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
