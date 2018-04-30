package com.jfms.engine.service.biz.remote.api;

public interface LastSeenRepository {
    void setLastSeen(String sourceUser, String destinationUser, Long seenTime);
    Long getLastSeen(String sourceUser, String destinationUser);
}
