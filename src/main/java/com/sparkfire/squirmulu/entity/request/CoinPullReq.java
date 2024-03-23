package com.sparkfire.squirmulu.entity.request;

import java.util.List;

public class CoinPullReq {
    private String pull_rule;
    private long target_id;
    private int coin_type;

    // Getter and Setter methods
    // ...
    public CoinPullReq() {
    }


    public CoinPullReq(String pull_rule, long target_id, int coin_type) {
        this.pull_rule = pull_rule;
        this.target_id = target_id;
        this.coin_type = coin_type;
    }

    public int getCoin_type() {
        return coin_type;
    }

    public void setCoin_type(int coin_type) {
        this.coin_type = coin_type;
    }

    public String getPull_rule() {
        return pull_rule;
    }

    public void setPull_rule(String pull_rule) {
        this.pull_rule = pull_rule;
    }

    public long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(long target_id) {
        this.target_id = target_id;
    }
}