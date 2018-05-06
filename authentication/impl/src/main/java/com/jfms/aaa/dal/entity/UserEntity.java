package com.jfms.aaa.dal.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


//@Entity
//@Table(name = "user_entity")
@Document(collection = "user_entity")
public class UserEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

//    @Column(unique = true)
    @Indexed
    private String mobileNumber;
//    @Column
    private String firstName;
//    @Column
    private String lastName;

//    protected UserEntity(){}

    public UserEntity(String mobileNumber, String firstName, String lastName) {
        this.mobileNumber = mobileNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
