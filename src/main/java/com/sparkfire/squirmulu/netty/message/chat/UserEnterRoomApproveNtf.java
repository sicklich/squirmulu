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
    private String message;

    public UserEnterRoomApproveNtf(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public UserEnterRoomApproveNtf() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
