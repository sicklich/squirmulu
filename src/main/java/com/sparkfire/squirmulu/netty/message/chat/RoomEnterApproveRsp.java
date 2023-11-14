package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 聊天发送消息结果的 Response
 */
public class RoomEnterApproveRsp implements Message {

    public static final String TYPE = "ROOM_ENTER_APPROVE_RSP";
    /**
     * 响应状态码 0 正常 1 已经在房间中 2 需要同意 -1 房间不存在
     */
    private Integer code;
    /**
     * 响应提示
     */
    private boolean approved;

    public RoomEnterApproveRsp(Integer code, boolean approved) {
        this.code = code;
        this.approved = approved;
    }

    public RoomEnterApproveRsp() {
    }

    public Integer getCode() {
        return code;
    }

    public RoomEnterApproveRsp setCode(Integer code) {
        this.code = code;
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public RoomEnterApproveRsp setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }
}
