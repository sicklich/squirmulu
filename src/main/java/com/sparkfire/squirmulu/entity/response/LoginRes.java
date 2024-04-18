package com.sparkfire.squirmulu.entity.response;

import com.sparkfire.squirmulu.pojo.SysUser;

import java.util.List;

public class LoginRes {

    private SysUser user;
    private String access_token;

    private int code = 0;

    public LoginRes(SysUser user, String access_token, int code) {
        this.user = user;
        this.access_token = access_token;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
