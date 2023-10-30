package com.sparkfire.squirmulu.entity.request;

public class DeleteRoomReq {
    private String id;

    public DeleteRoomReq() {
    }

    public DeleteRoomReq(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
