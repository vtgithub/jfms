package com.jfms.message_history.dal.entity;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table
public class P2PUpdateEntity implements Serializable {
    @PrimaryKeyColumn(
            name = "id",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 2,
            ordering = Ordering.DESCENDING
    )
    private UUID id;

    @PrimaryKeyColumn(
            name = "editor",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 0,
            ordering = Ordering.ASCENDING
    )
    private String editor;
    @PrimaryKeyColumn(
            name = "messageId",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 1,
            ordering = Ordering.ASCENDING
    )
    private String messageId;

    private String previousValue;
    private String currentValue;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}
