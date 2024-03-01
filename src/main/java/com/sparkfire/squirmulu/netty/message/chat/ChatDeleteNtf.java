package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 发送给所有人的群聊消息的 Message
 */
public class ChatDeleteNtf implements Message {

    public static final String TYPE = "CHAT_DELETE_NTF";

    /**
     * 消息编号
     */
    private long id;

    private int chat_type;


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
}
