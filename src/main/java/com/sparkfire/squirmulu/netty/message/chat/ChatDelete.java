package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 发送给所有人的群聊消息的 Message
 */
public class ChatDelete implements Message {

    public static final String TYPE = "CHAT_DELETE";

    /**
     * 消息编号
     */
    private long id;

    private int chat_type;

    private long room_id;

    private String c_type;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getChat_type() {
        return chat_type;
    }

    public void setChat_type(int chat_type) {
        this.chat_type = chat_type;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }
}
