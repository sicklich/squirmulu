package com.sparkfire.squirmulu.entity;

import java.util.List;

public class RoomCardUpdateRes {
    private boolean join_status;

    public RoomCardUpdateRes(boolean join_status) {
        this.join_status = join_status;
    }

    public RoomCardUpdateRes() {
    }

    public boolean isJoin_status() {
        return join_status;
    }

    public void setJoin_status(boolean join_status) {
        this.join_status = join_status;
    }
}
