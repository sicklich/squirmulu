package com.sparkfire.squirmulu.entity.request;

public class DeleteCardReq {
    private String id;

    public DeleteCardReq() {
    }

    public DeleteCardReq(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
