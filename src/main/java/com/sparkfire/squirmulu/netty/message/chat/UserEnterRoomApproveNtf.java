package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 进房时的消息提示
 */
public class UserEnterRoomApproveNtf implements Message {

    public static final String TYPE = "USER_ENTER_ROOM_APPROVE_NTF";

    /**
     * 响应状态码 0 同意 -1 不同意
     */
    private Integer code;
    /**
     * 响应提示
     */
    private long room_id;
    /**
     * 用户id
     */
    private long user_id;
    private String roomname;
    private String nickname;
    private String card_id;

    public UserEnterRoomApproveNtf(Integer code, long room_id, long user_id, String roomname, String nickname, String card_id) {
        this.code = code;
        this.room_id = room_id;
        this.user_id = user_id;
        this.roomname = roomname;
        this.nickname = nickname;
        this.card_id = card_id;
    }

    public UserEnterRoomApproveNtf() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
}
