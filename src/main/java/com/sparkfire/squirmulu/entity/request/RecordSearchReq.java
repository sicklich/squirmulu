package com.sparkfire.squirmulu.entity.request;

public class RecordSearchReq {
    private long room_id;
    private int chat_type;
    private int p_channel;
    private int page_cur;
    private int page_size;
    private String keywords;
    private int searching_mode;

    public RecordSearchReq(long room_id, int chat_type, int p_channel, int page_cur, int page_size, String keywords, int searching_mode) {
        this.room_id = room_id;
        this.chat_type = chat_type;
        this.p_channel = p_channel;
        this.page_cur = page_cur;
        this.page_size = page_size;
        this.keywords = keywords;
        this.searching_mode = searching_mode;
    }

    public RecordSearchReq() {
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public int getChat_type() {
        return chat_type;
    }

    public void setChat_type(int chat_type) {
        this.chat_type = chat_type;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getP_channel() {
        return p_channel;
    }

    public void setP_channel(int p_channel) {
        this.p_channel = p_channel;
    }

    public int getPage_cur() {
        return page_cur;
    }

    public void setPage_cur(int page_cur) {
        this.page_cur = page_cur;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getSearching_mode() {
        return searching_mode;
    }

    public void setSearching_mode(int searching_mode) {
        this.searching_mode = searching_mode;
    }
}
