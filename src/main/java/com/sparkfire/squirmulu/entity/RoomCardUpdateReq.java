package com.sparkfire.squirmulu.entity;

import java.util.List;

public class RoomCardUpdateReq {
    private String id;

    private String card_id;

    private String user_id;

    private List<RoomCardUpdateTarget> targets;

    public RoomCardUpdateReq(String id, String card_id, String user_id, List<RoomCardUpdateTarget> targets) {
        this.id = id;
        this.card_id = card_id;
        this.user_id = user_id;
        this.targets = targets;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
