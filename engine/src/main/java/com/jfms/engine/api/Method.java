package com.jfms.engine.api;

public enum Method {
    INIT(1),
    SEND(2),
    EDIT(3),
    DELETE(4),
    IS_TYPING(5),
    PING(6),
    CONVERSATION_LEAVE(7),
    CONVERSATION_IN(8);

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    Method(int value) {
        this.value = value;
    }
}
