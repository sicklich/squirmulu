package com.sparkfire.squirmulu.config;

public enum RoomStatus {
    EDITING("正在编辑",0),
    RECRUITING("玩家招募",1),
    IN_GAME("正在游戏",2),
    PAUSE("游戏暂停",3),
    FINISHED("游戏结束",4);

    private String statusName;
    private int statusValue;

    RoomStatus(String statusName, int statusValue) {
        this.statusName = statusName;
        this.statusValue = statusValue;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }
}
