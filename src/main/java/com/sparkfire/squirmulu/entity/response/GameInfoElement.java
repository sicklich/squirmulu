package com.sparkfire.squirmulu.entity.response;

public class GameInfoElement {
    private String target;
    private String info;

    public GameInfoElement(String target, String info) {
        this.target = target;
        this.info = info;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
