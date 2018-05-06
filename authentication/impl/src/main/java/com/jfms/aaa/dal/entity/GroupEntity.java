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
    private String displayName;
    private String owner;
    private List<GroupMemberObject> memberObjectList;
    private Integer type;

    public GroupEntity(String displayName, String owner, List<GroupMemberObject> memberObjectList, Integer type) {
        this.displayName = displayName;
        this.owner = owner;
        this.memberObjectList = memberObjectList;
        this.type = type;
    }

    public GroupEntity() {
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
