package com.sparkfire.squirmulu.entity;

import java.util.List;

public class IndexTarget {
    private String target;
    private int level;
    private List<String> keys;
    private String value;

    public IndexTarget(String target, int level, List<String> keys, String value) {
        this.target = target;
        this.level = level;
        this.keys = keys;
        this.value = value;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
