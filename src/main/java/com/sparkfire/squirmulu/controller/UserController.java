package com.sparkfire.squirmulu.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparkfire.squirmulu.common.Result;
import com.sparkfire.squirmulu.dao.ImgDao;
import com.sparkfire.squirmulu.dao.InvitationCodeDao;
import com.sparkfire.squirmulu.dao.MessageDao;
import com.sparkfire.squirmulu.entity.*;
import com.sparkfire.squirmulu.entity.request.MyImgReq;
import com.sparkfire.squirmulu.entity.request.MyMsgReq;
import com.sparkfire.squirmulu.entity.request.MyPlayerCardListReq;
import com.sparkfire.squirmulu.entity.request.MyRoomListReq;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.mapper.SysUserMapper;
import com.sparkfire.squirmulu.pojo.SysUser;
import com.sparkfire.squirmulu.service.CardService;
import com.sparkfire.squirmulu.service.RoomService;
import com.sparkfire.squirmulu.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private InvitationCodeDao invitationCodeDao;

    @Autowired
    private ImgDao imgDao;

    @Value("${http.path}")
    private String httpPath;

    @Autowired
    private MessageDao messageDao;

    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

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
    public Result<Boolean> create(@RequestBody RegisterReq req) {
        // 验证邀请码是否有效
        InvitationCode code = invitationCodeDao.findByCode(req.getInvitation_code());
        if (code == null || code.getIs_used() == 1) {
            return Result.fail(-1, "Invalid invitation code");
        }

        // 在这里执行正常的注册逻辑，如保存用户信息到数据库等
        SysUser user = new SysUser(req.getEmail(),req.getPwd(),req.getTelephone(),req.getNickname());
        sysUserService.insert(user);
        SysUser userINDB = sysUserMapper.getSysUserInfo(req.getEmail(), req.getPwd());

        // 将邀请码标记为已使用，并设置关联的用户 ID
        long now = System.currentTimeMillis()/1000;
        code.setIs_used(1);
        code.setUser_id(userINDB.getId());
        code.setUse_time(now);
        code.setCode(req.getInvitation_code());
        invitationCodeDao.update(code);

        return Result.ok(true);
    }

    @RequestMapping("/user-account/update-info")
    public CommonResponse update(@RequestBody SysUser user) {
        return CommonResponse.success(sysUserService.update(user));
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
        return Result.ok(sysUserService.getUserInfoById(id));
    }

    @RequestMapping("/user-card/pull-rolecard-list")
    public CommonResponse my_card_list(@RequestBody MyPlayerCardListReq req, HttpServletRequest request) throws JsonProcessingException {
        String userId = (String) request.getAttribute("userId");
        return cardService.myCardList(req, Long.parseLong(userId));
    }

    @RequestMapping("/user-room/pull-room-list")
    public CommonResponse my_room_list(@RequestBody MyRoomListReq req) throws JsonProcessingException {
        return roomService.myRoomList(req);
    }

    @PostMapping("/owned/pull-img-list")
    public CommonResponse<List<String>> myImage(@RequestBody() MyImgReq req) {
        return CommonResponse.success(imgDao.getByIDAndType(req.getId(), req.getType()).stream()
                .skip(req.getNum_cur()).limit(req.getPage_size()).map(file -> httpPath + file)
                .collect(Collectors.toList()));
    }

    @PostMapping("/user-msg/pull-history-msg")
    public CommonResponse<List<MessageDBWithIDString>> pullHistoryMessage(@RequestBody() MyMsgReq req) {
        return CommonResponse.success(messageDao.getByPage(req.getUser_id(), req.getType(), req.getNum_cur(), req.getPage_size()).stream()
                .map(msg -> new MessageDBWithIDString(msg.getId() + "", msg.getUser_id(), msg.getType(), msg.getBackend_type(), msg.getMessage_body(), msg.getC_time(), msg.getStatus()))
                .collect(Collectors.toList()));
    }

}
