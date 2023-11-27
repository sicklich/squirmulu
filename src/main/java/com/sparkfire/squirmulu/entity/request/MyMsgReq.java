package com.sparkfire.squirmulu.entity.request;

public class MyMsgReq {
    private long user_id;
    private int type;

    private int page_cur;
    private int page_size;

    public MyMsgReq() {
    }

    public MyMsgReq(long user_id, int type, int page_cur, int page_size) {
        this.user_id = user_id;
        this.type = type;
        this.page_cur = page_cur;
        this.page_size = page_size;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getPage_cur() {
        return page_cur;
    }

    public void setPage_cur(int page_cur) {
        this.page_cur = page_cur;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
