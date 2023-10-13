package com.sparkfire.squirmulu.mapper;

import com.sparkfire.squirmulu.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int insert(SysUser row);

    SysUser getSysUserInfoByEmail(String email);

    SysUser getSysUserInfo(@Param("email") String email, @Param("pwd") String pwd);

    SysUser getSysUserInfoById(Long id);

    int update(SysUser user);

    int updateLastSignInTime(Long id);
}