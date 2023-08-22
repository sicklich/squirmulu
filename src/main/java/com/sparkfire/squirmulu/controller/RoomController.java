package com.sparkfire.squirmulu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/squ/game/game-deal")
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/new-room")
    public CommonResponse new_room(@RequestParam(value = "body_info", required = true) String body_info, @RequestParam(value = "kp_id") long kp_id) {
        System.out.println("recv");
        return CommonResponse.success(roomService.createRoom(body_info, kp_id));
    }

    @GetMapping("/update-room")
    public CommonResponse update_room(@RequestBody IndexBody indexBody) throws JsonProcessingException {
        return CommonResponse.success(roomService.updateRoom(indexBody));
    }

    @GetMapping("/publish-room")
    public CommonResponse publish_room(@RequestParam(value = "game_room") String game_room, @RequestParam(value = "game_room") String id) throws JsonProcessingException {
        return CommonResponse.success(roomService.publish(game_room, id));
    }

}
