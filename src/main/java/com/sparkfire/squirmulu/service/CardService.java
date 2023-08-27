package com.sparkfire.squirmulu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparkfire.squirmulu.config.RoomListCondition;
import com.sparkfire.squirmulu.config.RoomStatus;
import com.sparkfire.squirmulu.dao.CardDao;
import com.sparkfire.squirmulu.dao.NpcCardDao;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.NpcCard;
import com.sparkfire.squirmulu.entity.PlayerCard;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.entity.response.GameListElement;
import com.sparkfire.squirmulu.entity.response.RoomListRes;
import com.sparkfire.squirmulu.entity.response.CommonGameRes;
import com.sparkfire.squirmulu.util.JsonUtil;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CardService {
    @Autowired
    NpcCardDao npcCardDao;
    @Autowired
    CardDao cardDao;

    @Autowired
    RedisClient redisClient;

    @Autowired
    ObjectMapper objectMapper;

    public CommonGameRes createNpcCard(NpcCard card) {
        long now = System.currentTimeMillis() / 1000;
        long id = SnowflakeGenerator.nextId();
        card.setNpc_mods(id);
        card.setP_time(now);
        card.setEdit_time(now);
        npcCardDao.insert(card);
        return new CommonGameRes(String.valueOf(id));
    }

    public CommonGameRes createPlayerCard(PlayerCard card) {
        long now = System.currentTimeMillis() / 1000;
        long id = SnowflakeGenerator.nextId();
        card.setId(id);
        card.setC_time(now);
        card.setM_time(now);
        cardDao.insert(card);
        return new CommonGameRes(String.valueOf(id));
//        roomDao.insert(info);
    }

    public CommonGameRes deleteNpcCard(long id) {
        npcCardDao.delete(id);
        return new CommonGameRes(String.valueOf(id));
    }

    public CommonGameRes deletePlayerCard(long id) {
        cardDao.delete(id);
        return new CommonGameRes(String.valueOf(id));
    }


    public CommonGameRes updateNpcCard(IndexBody body) throws JsonProcessingException {
        NpcCard card = npcCardDao.get(body.getId());
        String edited = JsonUtil.updateKeyForJsonBody(card.getBody_info(), body.getTargets());
        card.setBody_info(edited);
        npcCardDao.update(card);
        return new CommonGameRes(String.valueOf(body.getId()));
    }

    public CommonGameRes updatePlayerCard(IndexBody body) throws JsonProcessingException {
        PlayerCard card = cardDao.get(body.getId());
        String edited = JsonUtil.updateKeyForJsonBody(card.getBody_info(), body.getTargets());
        card.setBody_info(edited);
        cardDao.update(card);
        return new CommonGameRes(String.valueOf(body.getId()));
    }
}
