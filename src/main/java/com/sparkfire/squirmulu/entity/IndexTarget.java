package com.sparkfire.squirmulu.entity;

import java.util.List;

public class IndexTarget {
    private static final int OBJECT = 1;
    private static final int ARRAY = 2;
    private String target;
    private int level;
    private List<String> keys;
    private String value;

    private int type = 0;


    public IndexTarget(String target, int level, List<String> keys, String value, int type) {
        this.target = target;
        this.level = level;
        this.keys = keys;
        this.value = value;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
