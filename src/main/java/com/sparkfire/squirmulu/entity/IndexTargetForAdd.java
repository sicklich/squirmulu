package com.sparkfire.squirmulu.entity;

import java.util.List;

public class IndexTargetForAdd {
    private String target;
    private int level;
    private List<String> keys;

    private String name;
    private String value;

    public IndexTargetForAdd(String target, int level, List<String> keys, String name, String value) {
        this.target = target;
        this.level = level;
        this.keys = keys;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
