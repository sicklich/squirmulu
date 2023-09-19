package com.sparkfire.squirmulu.entity.request;

public class PullCardReq {
    private long id;

    public PullCardReq() {
    }

    public PullCardReq(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
