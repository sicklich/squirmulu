package com.sparkfire.squirmulu.netty.message.heartbeat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 消息 - 心跳请求
 */
public class HeartbeatRequest implements Message {

    /**
     * 类型 - 心跳请求
     */
    public static final String TYPE = "HEARTBEAT_REQUEST";

    private String user_id = "";

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "HeartbeatRequest{}";
    }

}
