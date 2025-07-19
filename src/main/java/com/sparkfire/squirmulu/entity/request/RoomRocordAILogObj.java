package com.sparkfire.squirmulu.entity.request;

public class RoomRocordAILogObj {
    private String room_id;
    private int chat_type;
    private RoomRocordAILogAnchors log_anchors;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public int getChat_type() {
        return chat_type;
    }

    public void setChat_type(int chat_type) {
        this.chat_type = chat_type;
    }

    public RoomRocordAILogAnchors getLog_anchors() {
        return log_anchors;
    }

    public void setLog_anchors(RoomRocordAILogAnchors log_anchors) {
        this.log_anchors = log_anchors;
    }
}
