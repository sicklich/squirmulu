package com.sparkfire.squirmulu.entity.request;

public class CoinPullRecord {
    private long id;
    private String avatar;
    private int coin_type;
    private int amount;

    // Getter and Setter methods
    // ...
    public CoinPullRecord() {
    }

    public CoinPullRecord(long id, String avatar, int coin_type, int amount) {
        this.id = id;
        this.avatar = avatar;
        this.coin_type = coin_type;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
}