package com.sparkfire.squirmulu.entity.request;

public class ChatListReq {
    private long room_id;
    private int chat_type;
    private int page_cur;
    private int page_size;

    public ChatListReq(long room_id, int chat_type, int page_cur, int page_size) {
        this.room_id = room_id;
        this.chat_type = chat_type;
        this.page_cur = page_cur;
        this.page_size = page_size;
    }

    public ChatListReq() {
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
}