package com.sparkfire.squirmulu.netty.message.heartbeat;


import com.sparkfire.squirmulu.netty.message.Message;
import com.sparkfire.squirmulu.netty.message.chat.RoomEnterResponse;

/**
 * 消息 - 心跳响应
 */
public class HeartbeatResponse implements Message {

    /**
     * 类型 - 心跳响应
     */
    public static final String TYPE = "HEARTBEAT_RESPONSE";

    @Override
    public String toString() {
        return "HeartbeatResponse{}";
    }

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

    public HeartbeatResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HeartbeatResponse setMessage(String message) {
        this.message = message;
        return this;
    }

}
