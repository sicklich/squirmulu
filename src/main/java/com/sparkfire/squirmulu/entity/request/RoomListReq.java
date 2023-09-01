package com.sparkfire.squirmulu.entity.request;

public class RoomListReq {
    private int page_cur;
    private int page_size;
    private int condition;

    public RoomListReq(int page_cur, int page_size, int condition) {
        this.page_cur = page_cur;
        this.page_size = page_size;
        this.condition = condition;
    }

    public RoomListReq() {
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

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}
