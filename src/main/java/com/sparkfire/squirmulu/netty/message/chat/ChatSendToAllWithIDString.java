package com.sparkfire.squirmulu.netty.message.chat;


import com.sparkfire.squirmulu.netty.message.Message;

/**
 * 发送给所有人的群聊消息的 Message
 */
public class ChatSendToAllWithIDString implements Message {

    public static final String TYPE = "CHAT_SEND_TO_ALL";

    /**
     * 消息编号
     */
    private String id;
    private int p_channel;
    private long p_time;
    /**
     * 内容
     */
    private String c_content;
    private String a_name;
    private String a_img;
    private String room_id;
    private long user_id;
    private String c_type;

    private int chat_type;

    public ChatSendToAllWithIDString() {
    }

    public ChatSendToAllWithIDString(String id, int p_channel, long p_time, String c_content, String a_name, String a_img, String room_id, long user_id, String c_type, int chat_type) {
        this.id = id;
        this.p_channel = p_channel;
        this.p_time = p_time;
        this.c_content = c_content;
        this.a_name = a_name;
        this.a_img = a_img;
        this.room_id = room_id;
        this.user_id = user_id;
        this.c_type = c_type;
        this.chat_type = chat_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getP_channel() {
        return p_channel;
    }

    public void setP_channel(int p_channel) {
        this.p_channel = p_channel;
    }

    public long getP_time() {
        return p_time;
    }

    public void setP_time(long p_time) {
        this.p_time = p_time;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_img() {
        return a_img;
    }

    public void setA_img(String a_img) {
        this.a_img = a_img;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public int getChat_type() {
        return chat_type;
    }

    public void setChat_type(int chat_type) {
        this.chat_type = chat_type;
    }
}
