package com.sparkfire.squirmulu.entity;

public class RoomInfoWrapper {
    private RoomInfo roomInfo;
    private String body_info;

    // Getters and setters

    public RoomInfoWrapper(RoomInfo roomInfo, String body_info) {
        this.roomInfo = roomInfo;
        this.body_info = body_info;
    }

    public RoomInfoWrapper() {
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    public String getBody_info() {
        return body_info;
    }

    public void setBody_info(String body_info) {
        this.body_info = body_info;
    }
}
