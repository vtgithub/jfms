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
    GROUP_INFO_EDIT(11),
    GROUP_SEND(12),
    GROUP_EDIT(13),
    GROUP_DELETE(14),
    GROUP_IS_TYPING(15),
    GROUP_CONVERSATION_LEAVE(16),
    CHANNEL_CREATION(17),
    CHANNEL_INFO_EDIT(18),
    CHANNEL_SEND(19),
    CHANNEL_EDIT(20),
    CHANNEL_DELETE(21),
    CHANNEL_IS_TYPING(22),
    CHANNEL_CONVERSATION_LEAVE(23);

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
