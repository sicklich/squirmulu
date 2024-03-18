package com.sparkfire.squirmulu.entity.request;

public class ClearMsgRes {
    private int state;
    private long room_id;
    private int type;
    private int p_channel;

    public ClearMsgRes(int state, long room_id, int type, int p_channel) {
        this.state = state;
        this.room_id = room_id;
        this.type = type;
        this.p_channel = p_channel;
    }

    public ClearMsgRes() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getP_channel() {
        return p_channel;
    }

    public void setP_channel(int p_channel) {
        this.p_channel = p_channel;
    }
}
