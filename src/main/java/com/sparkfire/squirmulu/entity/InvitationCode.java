package com.sparkfire.squirmulu.entity;

public class InvitationCode {
    private String code;
    private int is_used;
    private long user_id;
    private long create_time;
    private long use_time;

    // Getters and setters for each field

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIs_used() {
        return is_used;
    }

    public void setIs_used(int is_used) {
        this.is_used = is_used;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUse_time() {
        return use_time;
    }

    public void setUse_time(long use_time) {
        this.use_time = use_time;
    }
}
