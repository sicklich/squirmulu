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
    private String roomname;
    private String nickname;
    private String card_id;

    private String ntfMsgId;
    private String enter_mode;

    public RoomEnterApproveReq(boolean approved, String user_id, String room_id, String roomname, String nickname, String card_id, String ntfMsgId, String enter_mode) {
        this.approved = approved;
        this.user_id = user_id;
        this.room_id = room_id;
        this.roomname = roomname;
        this.nickname = nickname;
        this.card_id = card_id;
        this.ntfMsgId = ntfMsgId;
        this.enter_mode = enter_mode;
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

    public String getNtfMsgId() {
        return ntfMsgId;
    }

    public void setNtfMsgId(String ntfMsgId) {
        this.ntfMsgId = ntfMsgId;
    }

    public String getEnter_mode() {
        return enter_mode;
    }

    public void setEnter_mode(String enter_mode) {
        this.enter_mode = enter_mode;
    }
}
