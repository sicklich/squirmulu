package com.sparkfire.squirmulu.entity.request;

public class MyMsgReq {
    private long user_id;
    private int type;

    private int num_cur;
    private int page_size;

    public MyMsgReq() {
    }

    public MyMsgReq(long user_id, int type, int num_cur, int page_size) {
        this.user_id = user_id;
        this.type = type;
        this.num_cur = num_cur;
        this.page_size = page_size;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getNum_cur() {
        return num_cur;
    }

    public void setNum_cur(int num_cur) {
        this.num_cur = num_cur;
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
