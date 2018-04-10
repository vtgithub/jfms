package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/9/18.
 */
public class JFMSServerDeleteMessage {
    private String id;
    private String from;

    public JFMSServerDeleteMessage(String id, String from) {
        this.id = id;
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
