package com.jfms.message_history.dal;

public enum EntityStatus {
    INSERTED((byte)1),
    UPDATED((byte)2),
    DELETED((byte)3);

    private byte status;

    EntityStatus(byte status) {
        this.status = status;
    }

    public byte getValue() {
        return INSERTED.status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
