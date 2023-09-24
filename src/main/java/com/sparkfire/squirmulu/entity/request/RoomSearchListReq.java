package com.sparkfire.squirmulu.entity.request;

public class RoomSearchListReq {
    private String key_words;
    private String search_type;

    public RoomSearchListReq(String key_words, String search_type) {
        this.key_words = key_words;
        this.search_type = search_type;
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
