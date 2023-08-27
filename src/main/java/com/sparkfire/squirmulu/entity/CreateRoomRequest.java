package com.sparkfire.squirmulu.entity;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public class CreateRoomRequest {

    private String body_info;
    private long kp_id;


    public CreateRoomRequest(String body_info, long kp_id) {
        this.body_info = body_info;
        this.kp_id = kp_id;
    }

    public CreateRoomRequest() {
    }

    public String getBody_info() {
        return body_info;
    }

    public void setBody_info(String body_info) {
        this.body_info = body_info;
    }

    public long getKp_id() {
        return kp_id;
    }

    public void setKp_id(long kp_id) {
        this.kp_id = kp_id;
    }
}
