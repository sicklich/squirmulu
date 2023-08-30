package com.sparkfire.squirmulu.entity.response;

public class GameListElement {
    private long id;
    private String r_info;

    public GameListElement(long id, String r_info) {
        this.id = id;
        this.r_info = r_info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getR_info() {
        return r_info;
    }

    public void setR_info(String r_info) {
        this.r_info = r_info;
    }
}