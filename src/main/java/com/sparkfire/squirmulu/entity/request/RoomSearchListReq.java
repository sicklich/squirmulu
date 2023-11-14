package com.sparkfire.squirmulu.entity.request;

public class RoomSearchListReq {
    private String key_words;
    private String search_type;

    private int page_cur;

    private int page_size;


    public RoomSearchListReq(String key_words, String search_type, int page_cur, int page_size) {
        this.key_words = key_words;
        this.search_type = search_type;
        this.page_cur = page_cur;
        this.page_size = page_size;
    }

    public RoomSearchListReq() {
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

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }
}
