package com.jfms.aaa.model;

public enum GroupType {

    REGULAR(1),
    SUPER(2);

    private Integer type;

    GroupType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
