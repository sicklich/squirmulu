package com.sparkfire.squirmulu.entity.request;

public class MyImgReq {
    private long userID;
    private int type;

    public MyImgReq() {
    }

    public MyImgReq(long userID, int type) {
        this.userID = userID;
        this.type = type;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
