package com.jfms.engine.service.biz.remote.api;

public interface PresenceRepository {
    void changePresenceTime(String from, Long pingTime);
}
