package com.jfms.offline_message.model;

/**
 * Created by vahid on 4/16/18.
 */
public class OfflineMessage {
    private String owner;
    private String message;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
