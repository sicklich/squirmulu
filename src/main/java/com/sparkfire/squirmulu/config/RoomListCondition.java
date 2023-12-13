package com.sparkfire.squirmulu.config;

import com.sparkfire.squirmulu.entity.RoomInfo;

import java.util.Comparator;

public enum RoomListCondition {
    DEFAULT("默认", 0),
    SHORTED("缺人", 1),
    FREE("自由", 2);

    private String statusName;
    private int statusValue;

    RoomListCondition(String statusName, int statusValue) {
        this.statusName = statusName;
        this.statusValue = statusValue;
    }

    public static RoomListCondition getByValue(int value) {
        for (RoomListCondition e : RoomListCondition.values()) {
            if (e.getStatusValue() == value) {
                return e;
            }
        }
        return null;
    }

    public Comparator<RoomInfo> getRoomConditionComparator() throws Exception {
        switch (statusValue) {
//            case 0:
//                return Comparator.comparing(RoomInfo::publishCycle)
//                        .thenComparing(RoomInfo::plShorted)
//                        .thenComparing(RoomInfo::getPublish_time);
//            case 1:
//                return Comparator.comparing(RoomInfo::publishCycle)
//                        .thenComparing(RoomInfo::plShorted)
//                        .thenComparing(RoomInfo::getG_time).reversed();
//            case 2:
//                return Comparator.comparing(RoomInfo::publishCycle)
//                        .thenComparing(RoomInfo::pwdNeeded)
//                        .thenComparing(RoomInfo::getG_time);
            default:
                throw new Exception("no such condition");
        }


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
