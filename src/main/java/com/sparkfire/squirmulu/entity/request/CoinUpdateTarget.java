package com.sparkfire.squirmulu.entity.request;

public class CoinUpdateTarget {
    private int coin_type;
    private int amount;
    private String update_rule;

    private long trade_no;

    // Getter and Setter methods
    // ...
    public CoinUpdateTarget() {
    }

    public CoinUpdateTarget(int coin_type, int amount, String update_rule, long trade_no) {
        this.coin_type = coin_type;
        this.amount = amount;
        this.update_rule = update_rule;
        this.trade_no = trade_no;
    }

    public long getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(long trade_no) {
        this.trade_no = trade_no;
    }

    public String getUpdate_rule() {
        return update_rule;
    }

    public void setUpdate_rule(String update_rule) {
        this.update_rule = update_rule;
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