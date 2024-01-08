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
    private String roomname;
    private String nickname;
    private String card_id;
    private long request_time;

    private String enter_mode;

    public UserEnterRoomNtf(long room_id, long user_id, String roomname, String nickname, String card_id, long request_time, String enter_mode) {
        this.room_id = room_id;
        this.user_id = user_id;
        this.roomname = roomname;
        this.nickname = nickname;
        this.card_id = card_id;
        this.request_time = request_time;
        this.enter_mode = enter_mode;
    }

    public UserEnterRoomNtf() {
    }

    public long getRequest_time() {
        return request_time;
    }

    public void setRequest_time(long request_time) {
        this.request_time = request_time;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
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

    public String getEnter_mode() {
        return enter_mode;
    }

    public void setEnter_mode(String enter_mode) {
        this.enter_mode = enter_mode;
    }
}
