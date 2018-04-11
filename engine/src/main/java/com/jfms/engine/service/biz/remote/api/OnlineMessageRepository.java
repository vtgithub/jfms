package com.jfms.engine.service.biz.remote.api;

public interface OnlineMessageRepository<E> {
    void sendMessage(String channel, String message);
    void setMessageListener(E messageListener);
}
