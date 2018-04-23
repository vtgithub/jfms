package com.jfms.message_history.dal.entity;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table
public class P2PEntity implements Serializable {
    @PrimaryKeyColumn(
            name = "id",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 2,
            ordering = Ordering.DESCENDING
    )
    private UUID id;
    @PrimaryKeyColumn(
            name = "owner",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 0,
            ordering = Ordering.ASCENDING
    )
    private String owner;
    @PrimaryKeyColumn(
            name = "messageId",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 1,
            ordering = Ordering.ASCENDING
    )
    private String messageId;
    @PrimaryKeyColumn(
            name = "from",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 2,
            ordering = Ordering.ASCENDING
    )
    private String from;
    @PrimaryKeyColumn(
            name = "time",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 3,
            ordering = Ordering.ASCENDING
    )
    private Long time;
    @PrimaryKeyColumn(
            name = "status",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 4,
            ordering = Ordering.ASCENDING
    )
    private Byte status;

    private String body;
    private String subject;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
