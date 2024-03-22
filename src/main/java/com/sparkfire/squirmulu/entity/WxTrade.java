package com.sparkfire.squirmulu.entity;

public class WxTrade {
    private long trade_no;
    private int status;
    private long user_id;
    private int total;
    private String description;
    private long create_time;
    private long edit_time;

    public WxTrade() {
    }

    public WxTrade(long trade_no, int status, long user_id, int total, String description, long create_time, long edit_time) {
        this.trade_no = trade_no;
        this.status = status;
        this.user_id = user_id;
        this.total = total;
        this.description = description;
        this.create_time = create_time;
        this.edit_time = edit_time;
    }

    public long getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(long trade_no) {
        this.trade_no = trade_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(long edit_time) {
        this.edit_time = edit_time;
    }
}