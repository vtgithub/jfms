package com.jfms.aaa.dal.entity;

import org.bson.types.ObjectId;
//import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


//@Entity
//@Table(name = "group_entity")
@Document(collection = "group_entity")
public class GroupEntity {
    @Id
    private ObjectId id;
    private Integer status;
    private String displayName;
    private List<String> displayNameChangeHistory;
    private String owner;
    private List<GroupMemberObject> memberObjectList;
    private Integer type;


    public GroupEntity(Integer status, String displayName, String owner, List<GroupMemberObject> memberObjectList, Integer type) {
        this.status = status;
        this.displayName = displayName;
        this.owner = owner;
        this.memberObjectList = memberObjectList;
        this.type = type;
    }

    public GroupEntity() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getDisplayNameChangeHistory() {
        return displayNameChangeHistory;
    }

    public void setDisplayNameChangeHistory(List<String> displayNameChangeHistory) {
        this.displayNameChangeHistory = displayNameChangeHistory;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<GroupMemberObject> getMemberObjectList() {
        return memberObjectList;
    }

    public void setMemberObjectList(List<GroupMemberObject> memberObjectList) {
        this.memberObjectList = memberObjectList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
