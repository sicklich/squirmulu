package com.sparkfire.squirmulu.entity.request;

import java.util.List;

public class CoinUpdateReq {
    private long id;
    private List<CoinUpdateTarget> targets;

    // Getter and Setter methods
    // ...
    public CoinUpdateReq() {
    }

    public CoinUpdateReq(long id, List<CoinUpdateTarget> targets) {
        this.id = id;
        this.targets = targets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CoinUpdateTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<CoinUpdateTarget> targets) {
        this.targets = targets;
    }
}