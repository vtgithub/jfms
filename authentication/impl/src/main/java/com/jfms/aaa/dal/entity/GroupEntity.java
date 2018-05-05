package com.jfms.aaa.dal.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "group_entity")
public class GroupEntity {
    @Id
    @Column("group_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID groupId;
    @Column
    private String displayName;
    @Column
    private String owner;


}
