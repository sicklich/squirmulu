package com.sparkfire.squirmulu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparkfire.squirmulu.config.RoomListCondition;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/squ/game")
//@CrossOrigin(origins = {"http://localhost:8081","http://localhost:5173"})
public class RoomController {
    @Autowired
    RoomService roomService;

    @RequestMapping("/game-deal/new-room")
    public CommonResponse new_room(@RequestBody RoomInfo info) throws UnsupportedEncodingException {
//        String decodedUrl = URLDecoder.decode(info.getBody_info(), StandardCharsets.UTF_8);
//        info.setBody_info(decodedUrl);
        return CommonResponse.success(roomService.createRoom(info));
    }

    @RequestMapping("/game-deal/delete-room")
    public CommonResponse delete_room(@RequestParam(value = "id", required = true) String id) {
        return CommonResponse.success(roomService.deleteRoom(id));
    }

    @RequestMapping("/game-deal/update-room")
    public CommonResponse update_room(@RequestBody IndexBody indexBody) throws JsonProcessingException {
        return CommonResponse.success(roomService.updateRoom(indexBody));
    }

    @RequestMapping("/game-into/pull-into")
    public CommonResponse pull_info(@RequestBody IndexBody indexBody) throws JsonProcessingException {
        return CommonResponse.success(roomService.getRoomInfo(indexBody));
    }

    @RequestMapping("/game-deal/publish-room")
    public CommonResponse publish_room(@RequestBody RoomInfo info) throws JsonProcessingException {
        return CommonResponse.success(roomService.publish(info));
    }

    @RequestMapping("/game-list/pull-condition")
    public CommonResponse pull_condition(@RequestParam(value = "page_cur") int page_cur
            , @RequestParam(value = "page_size") int page_size, @RequestParam(value = "condition") int condition) throws JsonProcessingException {
        return CommonResponse.success(roomService.getRoomList(RoomListCondition.getByValue(condition),page_cur,page_size));
    }

}
