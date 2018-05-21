package com.jfms.aaa.model;

public class UserInfo {
    private String mobileNumber;
    private String firstName;
    private String lastName;

    public UserInfo() {
    }

    public UserInfo(String mobileNumber, String firstName, String lastName) {
        this.mobileNumber = mobileNumber;
        this.firstName = firstName;
        this.lastName = lastName;
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
