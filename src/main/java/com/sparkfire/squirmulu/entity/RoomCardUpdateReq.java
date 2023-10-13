package com.sparkfire.squirmulu.entity;

import java.util.List;

public class RoomCardUpdateReq {
    private String id;

    private String card_id;
    private List<RoomCardUpdateTarget> targets;

    public RoomCardUpdateReq(String id, String card_id, List<RoomCardUpdateTarget> targets) {
        this.id = id;
        this.card_id = card_id;
        this.targets = targets;
    }

    public RoomCardUpdateReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public List<RoomCardUpdateTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<RoomCardUpdateTarget> targets) {
        this.targets = targets;
    }
}
