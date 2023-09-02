package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 发送给指定人的私聊消息 Request
 */
public class UserEnterRoomRequest implements Message {

    public static final String TYPE = "USER_ENTER_ROOM_REQUEST";

    /**
     * 房间id
     */
    private long room_id;
    /**
     * 用户id
     */
    private long user_id;

    public UserEnterRoomRequest(long room_id, long user_id) {
        this.room_id = room_id;
        this.user_id = user_id;
    }

    public UserEnterRoomRequest() {
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
