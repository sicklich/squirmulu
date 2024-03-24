package com.sparkfire.squirmulu.netty.message;


/**
 * 进房时的消息提示
 */
public class WxPayNtf implements Message {

    public static final String TYPE = "WECHAT_PAY_NTF";

    /**
     * 房间id
     */
    private int code;
    /**
     * 用户id
     */
    private String msg;
    private String id;

    public WxPayNtf() {
    }

    public WxPayNtf(int code, String msg, String id) {
        this.code = code;
        this.msg = msg;
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
