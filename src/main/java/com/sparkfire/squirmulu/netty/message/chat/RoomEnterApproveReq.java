package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 聊天发送消息结果的 Response
 */
public class RoomEnterApproveReq implements Message {

    public static final String TYPE = "ROOM_ENTER_APPROVE_REQ";
    /**
     * 响应提示
     */
    private boolean approved;
    private String user_id;
    private String room_id;

    public RoomEnterApproveReq(boolean approved, String user_id, String room_id) {
        this.approved = approved;
        this.user_id = user_id;
        this.room_id = room_id;
    }

    public RoomEnterApproveReq() {
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
