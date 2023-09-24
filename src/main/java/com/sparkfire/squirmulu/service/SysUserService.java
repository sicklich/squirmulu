package com.sparkfire.squirmulu.service;


import com.sparkfire.squirmulu.entity.response.LoginRes;
import com.sparkfire.squirmulu.pojo.SysUser;

import java.util.Map;

public interface SysUserService
{

    public Boolean insert(SysUser sysUser);

    Map<String, Object> login(String email, String pwd);

    LoginRes loginV2(String email, String pwd);


    SysUser getUserInfoById(Long id);
    //  public int update( SysUser sysUser );
//
// public int deleteById(Long id);
}