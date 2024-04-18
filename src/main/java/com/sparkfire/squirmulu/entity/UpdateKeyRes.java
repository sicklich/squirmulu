package com.sparkfire.squirmulu.entity;

public class UpdateKeyRes {
    String edited;
    int idx;

    public UpdateKeyRes() {
    }

    public UpdateKeyRes(String edited, int idx) {
        this.edited = edited;
        this.idx = idx;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
