package com.sparkfire.squirmulu.entity;

public class MessageDBWithIDString {
    private String id;
    private long user_id;
    private int type;
    private String backend_type;
    private String message_body;

    private long c_time;

    private int status;

    public MessageDBWithIDString(String id, long user_id, int type, String backend_type, String message_body, long c_time, int status) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.backend_type = backend_type;
        this.message_body = message_body;
        this.c_time = c_time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBackend_type() {
        return backend_type;
    }

    public void setBackend_type(String backend_type) {
        this.backend_type = backend_type;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public long getC_time() {
        return c_time;
    }

    public void setC_time(long c_time) {
        this.c_time = c_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
