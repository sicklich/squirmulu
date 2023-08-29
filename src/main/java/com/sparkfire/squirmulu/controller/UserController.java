package com.sparkfire.squirmulu.controller;


import com.sparkfire.squirmulu.common.Result;
import com.sparkfire.squirmulu.pojo.SysUser;
import com.sparkfire.squirmulu.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "用户信息")
@RestController
@RequestMapping("/squ/user/")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user-account/login")
    @ApiOperation("用户登录")
    public Result<Map<String, Object>> login(@RequestBody SysUser user) {
        return Result.ok(sysUserService.login(user.getEmail(), user.getPwd()));
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/user-account/register")
    @ApiOperation("注册")
    public Result<Boolean> create(@RequestBody SysUser user) {
        return Result.ok(sysUserService.insert(user) );
    }


    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("用户详情")
    public Result<SysUser> getUserInfo(@PathVariable("id") Long id) {
        return Result.ok(sysUserService.getUserInfoById(id) );
    }

}
