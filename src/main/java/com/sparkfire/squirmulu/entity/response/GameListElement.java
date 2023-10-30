package com.sparkfire.squirmulu.entity.response;

public class GameListElement {
    private String id;

    private String pwd;
    private String r_info;

    public GameListElement(String id, String pwd, String r_info) {
        this.id = id;
        this.pwd = pwd;
        this.r_info = r_info;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getR_info() {
        return r_info;
    }

    public void setR_info(String r_info) {
        this.r_info = r_info;
    }
}
