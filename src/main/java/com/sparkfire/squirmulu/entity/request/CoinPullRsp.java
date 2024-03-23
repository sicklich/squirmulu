package com.sparkfire.squirmulu.entity.request;

import java.util.List;

public class CoinPullRsp {
    private List<CoinPullRecord> user_list;

    // Getter and Setter methods
    // ...
    public CoinPullRsp() {
    }

    public CoinPullRsp(List<CoinPullRecord> user_list) {
        this.user_list = user_list;
    }

    public List<CoinPullRecord> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<CoinPullRecord> user_list) {
        this.user_list = user_list;
    }

}