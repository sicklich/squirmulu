package com.sparkfire.squirmulu.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;

import com.sparkfire.squirmulu.dto.LoginUser;
import com.sparkfire.squirmulu.exception.ServiceException;
import com.sparkfire.squirmulu.mapper.SysUserMapper;
import com.sparkfire.squirmulu.pojo.SysUser;
import com.sparkfire.squirmulu.service.SysUserService;
import com.sparkfire.squirmulu.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
*
*/
@Service
public class SysUserServiceImpl implements SysUserService
{

    @Autowired (required=false)
    private SysUserMapper sysUserMapper;
    @Autowired
    private TokenService tokenService;


    public Boolean insert(SysUser sysUser) {
        // 用户名或密码为空 错误
        if (!Validator.isEmail(sysUser.getEmail()))
        {
            throw new ServiceException("邮箱输入错误");
        }

        SysUser user = sysUserMapper.getSysUserInfoByEmail(sysUser.getEmail());
        if (Objects.nonNull(user))
        {
            throw new ServiceException("该邮箱已注册");
        }

        if (StrUtil.isBlank(sysUser.getPwd()))
        {
            throw new ServiceException("密码不能为空");
        }
        if (!PhoneUtil.isMobile(sysUser.getTelephone()))
        {
            throw new ServiceException("手机号输入错误");
        }
        if (StrUtil.isBlank(sysUser.getNickname()))
        {
            throw new ServiceException("昵称不能为空");
        }
        sysUser.setCreateTime(new Date());
        sysUser.setAuth(1);
        return sysUserMapper.insert(sysUser)>0;
    }

    @Override
    public Map<String, Object> login(String email, String pwd)
    {
        if (!Validator.isEmail(email))
        {
            throw new ServiceException("邮箱输入错误");
        }
        if (StrUtil.isBlank(pwd))
        {
            throw new ServiceException("密码不能为空");
        }
        SysUser user = sysUserMapper.getSysUserInfo(email,pwd);
        if (Objects.isNull(user)) {
            throw new ServiceException("登录用户：" + email + " 不存在 或密码错误");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setLoginName(user.getEmail());

        Map<String, Object> token = tokenService.createToken(loginUser);
        token.put("id",user.getId());

        sysUserMapper.updateLastSignInTime(user.getId());

        return token;
    }

    @Override
    public SysUser getUserInfoById(Long id)
    {
        return sysUserMapper.getSysUserInfoById(id);
    }
}