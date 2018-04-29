package com.jfms.engine.service.biz.remote.api;

public interface PresenceRepository {
    void changePresenceTime(String from, Long pingTime);
    void setPresenceStatus(String user, String status);
    String getPresenceStatus(String user);
}
