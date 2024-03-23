package com.sparkfire.squirmulu.entity.request;

import java.util.List;

public class CoinUpdateRsp {
    private List<CoinUpdateTarget> targets;

    // Getter and Setter methods
    // ...
    public CoinUpdateRsp() {
    }

    public CoinUpdateRsp(List<CoinUpdateTarget> targets) {
        this.targets = targets;
    }

    public List<CoinUpdateTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<CoinUpdateTarget> targets) {
        this.targets = targets;
    }
}