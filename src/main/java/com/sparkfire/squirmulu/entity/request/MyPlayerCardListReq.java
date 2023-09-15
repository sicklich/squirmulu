package com.sparkfire.squirmulu.entity.request;

public class MyPlayerCardListReq {
    private int page_cur;
    private int page_size;
    private String type;
    private long id;

    public MyPlayerCardListReq(int page_cur, int page_size, String type, long id) {
        this.page_cur = page_cur;
        this.page_size = page_size;
        this.type = type;
        this.id = id;
    }

    public MyPlayerCardListReq() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
