package com.sparkfire.squirmulu.config;

public enum ChatCategory {
    GAME("场内消息",1),
    CHIT_CHAT("场外闲聊",2)
    ;
    private String name;
    private int Value;

    ChatCategory(String name, int value) {
        this.name = name;
        Value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }
}
