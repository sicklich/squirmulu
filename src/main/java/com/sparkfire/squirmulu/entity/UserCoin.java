package com.sparkfire.squirmulu.entity;

public class UserCoin {
    private long id;
    private int coin_type;
    private int amount;
    private long create_time;

    private long edit_time;

    // Getter and Setter methods
    // ...
    public UserCoin() {
    }

    public UserCoin(long id, int coin_type, int amount, long create_time, long edit_time) {
        this.id = id;
        this.coin_type = coin_type;
        this.amount = amount;
        this.create_time = create_time;
        this.edit_time = edit_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCoin_type() {
        return coin_type;
    }

    public void setCoin_type(int coin_type) {
        this.coin_type = coin_type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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