package com.jfms.aaa.dal;

public enum EntityStatus {
    ACTIVE(1),
    INACTIVE(2);

    private Integer status;

    EntityStatus(Integer status) {
        this.status = status;
    }

    public Integer value() {
        return status;
    }
}
