package com.sparkfire.squirmulu.entity.request;

public class SaveChatListReq {
    private int chat_type;
    private int test;

    public SaveChatListReq(int chat_type, int test) {
        this.chat_type = chat_type;
        this.test = test;
    }

    public SaveChatListReq() {
    }

    public int getChat_type() {
        return chat_type;
    }

    public void setChat_type(int chat_type) {
        this.chat_type = chat_type;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }
}
