package com.sparkfire.squirmulu.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparkfire.squirmulu.common.Result;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.request.MyPlayerCardListReq;
import com.sparkfire.squirmulu.entity.request.MyRoomListReq;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.pojo.SysUser;
import com.sparkfire.squirmulu.service.CardService;
import com.sparkfire.squirmulu.service.RoomService;
import com.sparkfire.squirmulu.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "用户信息")
@RestController
@RequestMapping("/squ/user/")
//@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CardService cardService;

    @Autowired
    private RoomService roomService;

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
//    @PostMapping("/user-account/login")
//    @ApiOperation("用户登录")
//    public Result<Map<String, Object>> login(@RequestBody SysUser user) {
//        return Result.ok(sysUserService.login(user.getEmail(), user.getPwd()));
//    }

    @PostMapping("/user-account/login")
    @ApiOperation("用户登录")
    public CommonResponse login(@RequestBody SysUser user) {
        return CommonResponse.success(sysUserService.loginV2(user.getEmail(), user.getPwd()));
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

    @RequestMapping("/user-card/pull-rolecard-list")
    public CommonResponse my_card_list(@RequestBody MyPlayerCardListReq req) throws JsonProcessingException {
        return cardService.myCardList(req);
    }

    @RequestMapping("/user-room/pull-room-list")
    public CommonResponse my_room_list(@RequestBody MyRoomListReq req) throws JsonProcessingException {
        return roomService.myRoomList(req);
    }

}
