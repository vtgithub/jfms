package com.jfms.engine.api;

public enum Method {
    INIT(1),
    SEND(2),
    EDIT(3),
    DELETE(4),
    IS_TYPING(5),
    PING(6),
    CONVERSATION_LEAVE(7),
    CONVERSATION_IN(8),
    SEEN(9),
    GROUP_CREATION(10),
    GROUP_SEND(11),
    GROUP_EDIT(12),
    GROUP_DELETE(13),
    GROUP_IS_TYPING(14),
    GROUP_CONVERSATION_LEAVE(15),
    GROUP_INFO_EDIT(15);

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
