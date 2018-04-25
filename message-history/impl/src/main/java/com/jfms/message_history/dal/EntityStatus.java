package com.jfms.message_history.dal;

public enum EntityStatus {
    INSERTED(1),
    UPDATED(2),
    DELETED(3);

    private Integer status;

    EntityStatus(Integer status) {
        this.status = status;
    }

    public Integer getValue() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
