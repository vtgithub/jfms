package com.jfms.aaa.dal.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


//@Entity
//@Table(name = "activation_entity")
@Document(collection = "activation_entity")
public class ActivationEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

//    @Column(nullable = false)
    @Indexed
    private String activationCode;
//    @Column
    private Long creationTime;
//    @Column
    private String mobileNumber;
//    @Column(nullable = false)
    private Boolean isUsed= false;

    public ActivationEntity() {
    }

    public ActivationEntity(String activationCode, String mobileNumber) {
        this.activationCode = activationCode;
        this.mobileNumber = mobileNumber;
        this.creationTime = System.currentTimeMillis();
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime() {
        this.creationTime = System.currentTimeMillis();
    }


}
