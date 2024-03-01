package com.sparkfire.squirmulu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sparkfire.squirmulu.dao.CardDao;
import com.sparkfire.squirmulu.dao.NpcCardDao;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.NpcCard;
import com.sparkfire.squirmulu.entity.PlayerCard;
import com.sparkfire.squirmulu.entity.PlayerCardIDString;
import com.sparkfire.squirmulu.entity.request.MyPlayerCardListReq;
import com.sparkfire.squirmulu.entity.response.CommonResponse;
import com.sparkfire.squirmulu.entity.response.CommonGameRes;
import com.sparkfire.squirmulu.exception.ServiceException;
import com.sparkfire.squirmulu.util.JsonUtil;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

    public CommonGameRes createPlayerCard(PlayerCard card) throws JsonProcessingException {
        long now = System.currentTimeMillis() / 1000;
        long id = SnowflakeGenerator.nextId();
        card.setId(id);
        card.setC_time(now);
        card.setM_time(now);
        ObjectNode cardJson = (ObjectNode) objectMapper.readTree(card.getRole_card());
        cardJson.put("id", id+"");
        card.setRole_card(objectMapper.writeValueAsString(cardJson));
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


    public CommonGameRes updateNpcCard(IndexBody body) {
        NpcCard card = npcCardDao.get(body.getId());
        String edited = JsonUtil.updateKeyForJsonBody(card.getBody_info(), body.getTargets());
        card.setBody_info(edited);
        npcCardDao.update(card);
        return new CommonGameRes(String.valueOf(body.getId()));
    }

    public CommonGameRes updatePlayerCard(IndexBody body, long userId) {
        PlayerCard card = cardDao.get(body.getId());
        if(null == card){
            throw new ServiceException("人物卡不存在！");
        }
        if(card.getCard_user() != userId){
            throw new ServiceException("无法修改他人的人物卡！");
        }
        String edited = JsonUtil.updateKeyForJsonBody(card.getRole_card(), body.getTargets());
        card.setRole_card(edited);
        card.setM_time(System.currentTimeMillis()/1000);
        cardDao.update(card);
        return new CommonGameRes(String.valueOf(body.getId()));
    }

    public CommonResponse pullPlayerCardByID(long id) throws JsonProcessingException {
        PlayerCard card = cardDao.get(id);
        return CommonResponse.success(new PlayerCardIDString(String.valueOf(card.getId()),card.getCard_creator(),card.getC_time(),card.getM_time(),card.getRole_card()));
    }

    public CommonResponse myCardList(MyPlayerCardListReq req, long userID) {
        if (!req.getType().equals("player")) {
            throw new ServiceException("不支持的查询类型");
        }
//        lists.stream().filter(card -> {
//            String bodyinfo = card.getRole_card();
//            try {
//                JsonNode node = objectMapper.readTree(bodyinfo);
//                return node.get("a_setting").get("card_user").asLong() == req.getId();
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        })
        List<PlayerCardIDString> list = cardDao.getByCardUser(userID).stream().map(card -> {
                    String bodyinfo = card.getRole_card();
                    try {
                        JsonNode node = objectMapper.readTree(bodyinfo);
                        if (node.isObject()) {
                            ObjectNode objectNode = (ObjectNode) node;
                            objectNode.retain("a_setting"); // 仅保留目标键值对
                            card.setRole_card(objectMapper.writeValueAsString(objectNode));
                        }
                        return new PlayerCardIDString(String.valueOf(card.getId()), card.getCard_creator(), card.getC_time(), card.getM_time(), card.getRole_card());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                //按时间排序
                .sorted(Comparator.comparing(PlayerCardIDString::getM_time).reversed())
                .skip((long) (req.getPage_cur() - 1) * req.getPage_size()).limit(req.getPage_size()).collect(Collectors.toList());


        return CommonResponse.success(list);

    }
}
