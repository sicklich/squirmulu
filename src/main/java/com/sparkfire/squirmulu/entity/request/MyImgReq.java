package com.sparkfire.squirmulu.entity.request;

public class MyImgReq {
    private long id;
    private int type;
    private int num_cur;
    private int page_size;

    public MyImgReq() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
