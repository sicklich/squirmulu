package com.sparkfire.squirmulu.entity.response;

import com.sparkfire.squirmulu.pojo.SysUser;

import java.util.List;

public class LoginRes {

    private SysUser user;
    private String access_token;

    public LoginRes(SysUser user, String access_token) {
        this.user = user;
        this.access_token = access_token;
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
