package com.sparkfire.squirmulu.mapper;

import com.sparkfire.squirmulu.entity.request.UpdatePwdReq;
import com.sparkfire.squirmulu.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int insert(SysUser row);

    SysUser getSysUserInfoByEmail(String email);

    SysUser getSysUserInfoByEmailAndTelephone(UpdatePwdReq req);

    SysUser getSysUserInfo(@Param("email") String email, @Param("pwd") String pwd);

    SysUser getSysUserInfoById(Long id);

    int update(SysUser user);

    int updatePwd(UpdatePwdReq req);

    int updateLastSignInTime(Long id);
}