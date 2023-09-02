package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 聊天发送消息结果的 Response
 */
public class RoomEnterResponse implements Message {

    public static final String TYPE = "ROOM_ENTER_RESPONSE";
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public RoomEnterResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RoomEnterResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
