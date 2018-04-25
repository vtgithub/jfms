package com.jfms.message_history.dal.entity;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table
public class P2PEntity implements Serializable {
    @PrimaryKeyColumn(
            name = "id",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 0
    )
    private UUID _id = UUID.randomUUID();

    @PrimaryKeyColumn(
            name = "owner",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 1
    )
    @Indexed
    private String owner;
    @PrimaryKeyColumn(
            name = "messageId",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 2
    )
    private String messageId;
    @PrimaryKeyColumn(
            name = "sender",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 3
    )
    private String sender;
    @PrimaryKeyColumn(
            name = "time",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 4
    )
    private Long time;
    @PrimaryKeyColumn(
            name = "status",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 5
    )
    private Integer status;
    private String body;
    private String subject;

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
