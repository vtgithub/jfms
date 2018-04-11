package com.jfms.engine.service.biz.remote.api;

public interface LastSeenRepository {
    void setLastSeen(String room, Long seenTime);
    Long getLastSeen(String room);
}
