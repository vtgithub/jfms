package com.jfms.engine.api.model;

/**
 * Created by vahid on 4/9/18.
 */
public class JFMSServerDeleteMessage {
    private String id;

    public JFMSServerDeleteMessage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
