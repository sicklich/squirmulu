package com.sparkfire.squirmulu.entity;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RoomDBInfo {
    private String id;
    private String body_info;
    private long kp_id;
    private long create_time;
    private long publish_time;
    protected long edit_time;
    private String r_name;
    private String r_des;

    private String r_tags;



//    public RoomInfo(String id, long kp_id, String body_info, long create_time,long edit_time, String r_name, String r_des, String r_tags) {
//        this.id = id;
//        this.body_info = body_info;
//        this.kp_id = kp_id;
//        this.create_time = create_time;
//        this.edit_time = edit_time;
//        this.r_name = r_name;
//        this.r_des = r_des;
//        this.r_tags = r_tags;
//    }
//
//    public RoomInfo(String id, int status, int pl_cur, int pl_max, String pwd, long g_time, String body_info, long kp_id, String kp_name, long create_time, long publish_time, long edit_time, String r_name, String r_des, String r_tags, boolean approve_required) {
//        this.id = id;
//        this.status = status;
//        this.pl_cur = pl_cur;
//        this.pl_max = pl_max;
//        this.pwd = pwd;
//        this.g_time = g_time;
//        this.body_info = body_info;
//        this.kp_id = kp_id;
//        this.kp_name = kp_name;
//        this.create_time = create_time;
//        this.publish_time = publish_time;
//        this.edit_time = edit_time;
//        this.r_name = r_name;
//        this.r_des = r_des;
//        this.r_tags = r_tags;
//        this.approve_required = approve_required;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody_info() {
        return body_info;
    }

    public long getKp_id() {
        return kp_id;
    }

    public void setKp_id(long kp_id) {
        this.kp_id = kp_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(long publish_time) {
        this.publish_time = publish_time;
    }

    public long getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(long edit_time) {
        this.edit_time = edit_time;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getR_des() {
        return r_des;
    }

    public void setR_des(String r_des) {
        this.r_des = r_des;
    }

    public String getR_tags() {
        return r_tags;
    }

    public void setR_tags(String r_tags) {
        this.r_tags = r_tags;
    }

    public void setBody_info(String body_info) {
        this.body_info = body_info;
    }

//    public void setBody_info(byte[] body_info) {this.body_info = new String(body_info, StandardCharsets.UTF_8);}
}
