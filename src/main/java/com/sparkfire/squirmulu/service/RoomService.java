package com.sparkfire.squirmulu.service;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.config.RoomListCondition;
import com.sparkfire.squirmulu.config.RoomStatus;
import com.sparkfire.squirmulu.dao.RoomDao;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.entity.response.GameListElement;
import com.sparkfire.squirmulu.entity.response.RoomListRes;
import com.sparkfire.squirmulu.entity.response.RoomRes;
import com.sparkfire.squirmulu.util.JsonUtil;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    RoomDao roomDao;
    @Autowired
    RedisClient redisClient;

    @Autowired
    ObjectMapper objectMapper;

    public RoomRes createRoom(String body_info, long kp_id) {
        long now = System.currentTimeMillis() / 1000;
        long id = SnowflakeGenerator.nextId();
        RoomInfo info = new RoomInfo(SnowflakeGenerator.nextId(), 0, 0, 0, "", 0L, body_info, kp_id, now, now, now);
        processBaseInfo(info);
        try {
            redisClient.addObject(RedisClient.room_list, String.valueOf(id), objectMapper.writeValueAsString(info));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new RoomRes(String.valueOf(info.getId()));
//        roomDao.insert(info);
    }

    public void processBaseInfo(RoomInfo info) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(info.getBody_info());
        } catch (JsonProcessingException e) {
            return;
        }
        info.setG_time(node.get("r_info").get("g_time").asLong());
        info.setPwd(node.get("r_setting").get("r_rule").get("r_acc").get("password").asText());
        info.setPl_cur(node.get("r_info").get("pl_cur").asInt());
        info.setPl_cur(node.get("r_info").get("pl_max").asInt());
    }

    public RoomRes updateRoom(IndexBody body) throws JsonProcessingException {
        String key = RedisClient.room_list;
        RoomInfo info = redisClient.getObjectById(key, String.valueOf(body.getId()), RoomInfo.class);
        String edited = JsonUtil.updateKeyForJsonBody(info.getBody_info(), body.getTargets());
        info.setBody_info(edited);
        ObjectMapper objectMapper = new ObjectMapper();
        //todo  需要测试 引用部分需要认真对待
        processBaseInfo(info);
        redisClient.addObject(key, String.valueOf(info.getId()), objectMapper.writeValueAsString(info));
        return new RoomRes(String.valueOf(info.getId()));
    }

    public RoomRes publish(String roomInfo, String id) throws JsonProcessingException {
        String key = RedisClient.room_list;
        RoomInfo info = redisClient.getObjectById(key, id, RoomInfo.class);
        info.setBody_info(roomInfo);
        info.setStatus(RoomStatus.RECRUITING.getStatusValue());
        ObjectMapper objectMapper = new ObjectMapper();
        redisClient.addObject(key, id, objectMapper.writeValueAsString(info));
        return new RoomRes(String.valueOf(info.getId()));
    }

    public RoomListRes getRoomList(RoomListCondition condition,int page_cur, int page_size) {
        //redis 获取 排序
        try {
            return new RoomListRes(redisClient.getAllObjects(RedisClient.room_list, RoomInfo.class).stream()
                    .filter(room -> room.getStatus() == RoomStatus.RECRUITING.getStatusValue())
                    .sorted(condition.getRoomConditionComparator())
                    .map(roomInfo -> new GameListElement(roomInfo.getId(), JsonUtil.get(roomInfo.getBody_info(), "r_info")))
                    .limit().collect(Collectors.toList()));
        } catch (Exception e) {
            return new RoomListRes(new ArrayList<>());
        }

    }
}
