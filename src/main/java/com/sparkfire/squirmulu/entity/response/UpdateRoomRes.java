package com.sparkfire.squirmulu.entity.response;

import com.sparkfire.squirmulu.pojo.SysUser;

public class UpdateRoomRes {

    private String id;

    private int source_card_idx;

    public UpdateRoomRes() {
    }

    public UpdateRoomRes(String id, int source_card_idx) {
        this.id = id;
        this.source_card_idx = source_card_idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSource_card_idx() {
        return source_card_idx;
    }

    public void setSource_card_idx(int source_card_idx) {
        this.source_card_idx = source_card_idx;
    }
}
