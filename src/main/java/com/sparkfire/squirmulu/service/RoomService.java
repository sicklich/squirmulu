package com.sparkfire.squirmulu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.config.RoomListCondition;
import com.sparkfire.squirmulu.config.RoomStatus;
import com.sparkfire.squirmulu.dao.RoomDao;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.IndexTarget;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.entity.RoomInfoWrapper;
import com.sparkfire.squirmulu.entity.response.*;
import com.sparkfire.squirmulu.util.JsonUtil;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    RoomDao roomDao;
    @Autowired
    RedisClient redisClient;

    @Autowired
    ObjectMapper objectMapper;

    public CommonGameRes createRoom(RoomInfo info) {
        long now = System.currentTimeMillis() / 1000;
        long id = SnowflakeGenerator.nextId();
        info.setId(id);
        info.setCreate_time(now);
        info.setEdit_time(now);
//        processBaseInfo(info);
        redisClient.addObject(RedisClient.room_list, String.valueOf(id), info);
        return new CommonGameRes(String.valueOf(id));
//        roomDao.insert(info);
    }

    public CommonGameRes deleteRoom(String id) {
        redisClient.deleteObject(RedisClient.room_list, id);
        return new CommonGameRes(id);
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

    public CommonGameRes updateRoom(IndexBody body) throws JsonProcessingException {
        String key = RedisClient.room_list;
        RoomInfo info = redisClient.getObjectById(key, String.valueOf(body.getId()), RoomInfo.class);
        String edited = JsonUtil.updateKeyForJsonBody(info.getBody_info(), body.getTargets());
        info.setBody_info(edited);
        //todo  需要测试 引用部分需要认真对待
        processBaseInfo(info);
        redisClient.addObject(key, String.valueOf(info.getId()), info);
        return new CommonGameRes(String.valueOf(info.getId()));
    }

    public RoomInfoRes getRoomInfo(IndexBody body) throws JsonProcessingException {
        String key = RedisClient.room_list;
        RoomInfo info = redisClient.getObjectById(key, String.valueOf(body.getId()), RoomInfo.class);
        List<GameInfoElement> elements = new ArrayList<>();
        for (IndexTarget target : body.getTargets()) {
            String gameInfo = JsonUtil.getByIndexTarget(info.getBody_info(), target);
            elements.add(new GameInfoElement(target.getTarget(), gameInfo));
        }
        return new RoomInfoRes(body.getId(), elements);
    }

    public CommonGameRes publish(RoomInfo roomInfo) throws JsonProcessingException {
        String key = RedisClient.room_list;
        roomInfo.setStatus(RoomStatus.RECRUITING.getStatusValue());
        String edited = JsonUtil.updateKeyForJsonBody(roomInfo.getBody_info(),List.of(new IndexTarget("r_state",2
                , List.of("r_info"),String.valueOf(RoomStatus.RECRUITING.getStatusValue()))));
        roomInfo.setBody_info(edited);
        redisClient.addObject(key, String.valueOf(roomInfo.getId()), roomInfo);
        return new CommonGameRes(String.valueOf(roomInfo.getId()));
    }

    public RoomListRes getRoomList(RoomListCondition condition, int page_cur, int page_size) {
        //redis 获取 排序
        try {
            List<GameListElement> list = redisClient.getAllObjects(RedisClient.room_list, RoomInfo.class).stream()
                    .filter(room -> room.getStatus() == RoomStatus.RECRUITING.getStatusValue())
                    .sorted(condition.getRoomConditionComparator())
                    .map(roomInfo -> new GameListElement(roomInfo.getId(), JsonUtil.get(roomInfo.getBody_info(), "r_info")))
                    .skip((long) page_size * (page_cur - 1)).limit(page_size)
                    .collect(Collectors.toList());
            return new RoomListRes(redisClient.getAllObjects(RedisClient.room_list, RoomInfo.class).stream()
                    .filter(room -> room.getStatus() == RoomStatus.RECRUITING.getStatusValue())
                    .sorted(condition.getRoomConditionComparator())
                    .map(roomInfo -> new GameListElement(roomInfo.getId(), JsonUtil.get(roomInfo.getBody_info(), "r_info")))
                    .skip((long) page_size * (page_cur - 1)).limit(page_size).collect(Collectors.toList()));
        } catch (Exception e) {
            return new RoomListRes(new ArrayList<>());
        }

    }
}
