package com.sparkfire.squirmulu.entity;

import java.util.List;

public class RoomCardUpdateTarget {
    private String mode;
    private String target;

    public RoomCardUpdateTarget(String mode, String target) {
        this.mode = mode;
        this.target = target;
    }

    public RoomCardUpdateTarget() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
