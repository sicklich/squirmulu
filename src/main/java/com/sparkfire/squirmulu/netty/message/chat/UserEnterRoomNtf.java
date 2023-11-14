package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 进房时的消息提示
 */
public class UserEnterRoomNtf implements Message {

    public static final String TYPE = "USER_ENTER_ROOM_NTF";

    /**
     * 房间id
     */
    private long room_id;
    /**
     * 用户id
     */
    private long user_id;

    public UserEnterRoomNtf(long room_id, long user_id) {
        this.room_id = room_id;
        this.user_id = user_id;
    }

    public UserEnterRoomNtf() {
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
