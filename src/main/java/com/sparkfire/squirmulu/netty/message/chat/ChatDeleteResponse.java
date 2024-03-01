package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 聊天发送消息结果的 Response
 */
public class ChatDeleteResponse implements Message {

    public static final String TYPE = "CHAT_DELETE_RESPONSE";

    /**
     * 消息编号
     */
    private long id;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;

    public long getId() {
        return id;
    }

    public ChatDeleteResponse setId(long id) {
        this.id = id;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public ChatDeleteResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ChatDeleteResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "ChatSendResponse{" +
                "id='" + id + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
