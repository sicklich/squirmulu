package com.sparkfire.squirmulu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sparkfire.squirmulu.config.RoomListCondition;
import com.sparkfire.squirmulu.config.RoomStatus;
import com.sparkfire.squirmulu.dao.RoomDao;
import com.sparkfire.squirmulu.entity.IndexBody;
import com.sparkfire.squirmulu.entity.IndexTarget;
import com.sparkfire.squirmulu.entity.PlayerCard;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.entity.request.ChatListReq;
import com.sparkfire.squirmulu.entity.request.MyPlayerCardListReq;
import com.sparkfire.squirmulu.entity.request.MyRoomListReq;
import com.sparkfire.squirmulu.entity.response.*;
import com.sparkfire.squirmulu.exception.ServiceException;
import com.sparkfire.squirmulu.netty.message.chat.ChatSendToAll;
import com.sparkfire.squirmulu.netty.messageHandler.chat.ChatSendToAllHandler;
import com.sparkfire.squirmulu.util.JsonUtil;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    RoomDao roomDao;

    @Autowired
    RedisClient redisClient;

    @Autowired
    ObjectMapper objectMapper;

    private static final String body_info = "{" +
            "  \"id\": \"\"," +
            "  \"kp_id\": \"\"," +
            "  \"r_info\": {" +
            "    \"r_img\": \"\"," +
            "    \"r_name\": \"\"," +
            "    \"kp_name\": \"\"," +
            "    \"c_time\": \"\"," +
            "    \"g_time\": \"\"," +
            "    \"pl_cur\": 0," +
            "    \"pl_max\": 4," +
            "    \"r_state\": 0," +
            "    \"r_tags\": [" +
            "      \"COC7th\"" +
            "    ]," +
            "    \"r_des\": \"\"" +
            "  }," +
            "  \"g_setting\": {" +
            "    \"g_type\": \"COC\"," +
            "    \"g_creator\": \"\"," +
            "    \"g_mods\": \"\"," +
            "    \"p_time\": \"\"," +
            "    \"c_price\": 0" +
            "  }," +
            "  \"r_setting\": {" +
            "    \"r_rule\": {" +
            "      \"r_acc\": {" +
            "        \"account\": \"\"," +
            "        \"password\": \"\"," +
            "        \"approve_required\": true" +
            "      }," +
            "      \"r_card\": {" +
            "        \"self_modi\": false," +
            "        \"cross_check\": false" +
            "      }," +
            "      \"r_check\": {" +
            "        \"succ\": 5," +
            "        \"fail\": 96," +
            "        \"strict_required\": true" +
            "      }," +
            "      \"r_result\": {" +
            "        \"self_roll\": true," +
            "        \"edit_forbidden\": false" +
            "      }," +
            "      \"r_more\": {" +
            "        \"statement\": \"\"" +
            "      }" +
            "    }" +
            "  }," +
            "  \"r_npc\": {" +
            "    \"npc_list\": [" +
            "      {" +
            "        \"npc_setting\": {" +
            "          \"creator\": \"\"," +
            "          \"npc_mods\": \"\"," +
            "          \"p_time\": \"\"," +
            "          \"c_price\": 0" +
            "        }," +
            "        \"npc_info\": {" +
            "          \"name\": \"\"," +
            "          \"img\": \"\"," +
            "          \"type\": \"\"," +
            "          \"intro\": \"\"" +
            "        }," +
            "        \"npc_state\": {" +
            "          \"hp_cur\": 0," +
            "          \"mp_cur\": 0," +
            "          \"damage\": \"\"" +
            "        }," +
            "        \"npc_att\": {" +
            "          \"str\": 0," +
            "          \"con\": 0," +
            "          \"siz\": 0," +
            "          \"dex\": 0," +
            "          \"app\": 0," +
            "          \"int\": 0," +
            "          \"pow\": 0," +
            "          \"edu\": 0," +
            "          \"luc\": 0" +
            "        }," +
            "        \"npc_extra\": {" +
            "          \"att\": [" +
            "            {" +
            "              \"name\": \"\"," +
            "              \"value\": 0" +
            "            }" +
            "          ]," +
            "          \"details\": \"\"" +
            "        }" +
            "      }" +
            "    ]" +
            "  }," +
            "  \"r_expo\": {" +
            "    \"expo_map\": {" +
            "      \"img\": \"\"," +
            "      \"intro\": \"\"" +
            "    }," +
            "    \"expo_scene_cur\": 0," +
            "    \"expo_scene\": []" +
            "  }," +
            "  \"g_gamers\": {" +
            "    \"g_keepers\": []," +
            "    \"g_players\": []," +
            "    \"g_audiences\": []" +
            "  }," +
            "  \"g_record\": {" +
            "    \"record_list\": []" +
            "  }," +
            "  \"g_chat\": {" +
            "    \"record_list\": []" +
            "  }," +
            "  \"g_clue\": {" +
            "    \"clue_list\": [" +
            "      {" +
            "        \"c_a_name\": \"\"," +
            "        \"c_content\": \"\"," +
            "        \"vis_status\": false" +
            "      }" +
            "    ]" +
            "  }" +
            "}";

    public CommonGameRes createRoom(RoomInfo info) {
        long now = System.currentTimeMillis() / 1000;
        long id = SnowflakeGenerator.nextId();
        info.setId(id);
        info.setCreate_time(now);
        info.setEdit_time(now);
        info.setBody_info(body_info);
        processBaseInfo(info);
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
        ((ObjectNode) node).put("id", String.valueOf(info.getId()));
        info.setPwd(node.get("r_setting").get("r_rule").get("r_acc").get("password").asText());
        ((ObjectNode) node.get("r_setting").get("r_rule").get("r_acc")).put("account", String.valueOf(info.getId()));
        ArrayNode arrayNode = (ArrayNode) node.get("g_gamers").get("g_keepers");
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("id", info.getKp_id());
        arrayNode.add(objectNode);
        info.setPl_cur(node.get("r_info").get("pl_cur").asInt());
        info.setPl_cur(node.get("r_info").get("pl_max").asInt());
        info.setBody_info(node.toString());
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
        if (body.getTargets().isEmpty()) {
            elements.add(new GameInfoElement("game_room", info.getBody_info()));
            return new RoomInfoRes(body.getId(), getAuthRole(info.getBody_info(), body.getUser_id()), elements);
        }
        for (IndexTarget target : body.getTargets()) {
            String gameInfo = JsonUtil.getByIndexTarget(info.getBody_info(), target);
            elements.add(new GameInfoElement(target.getTarget(), gameInfo));
        }
        return new RoomInfoRes(body.getId(), getAuthRole(info.getBody_info(), body.getUser_id()), elements);
    }

    public List<ChatSendToAll> getChatList(ChatListReq req) {
        String key = (req.getChat_type() == ChatSendToAllHandler.CHAT ?
                RedisClient.room_chat_list : RedisClient.room_record_list) + req.getRoom_id();
        long start = (long) (req.getPage_cur() - 1) * req.getPage_size();
        long end = start + req.getPage_size() - 1;
        return redisClient.zRange(key, start, end, ChatSendToAll.class).stream()
                .sorted(Comparator.comparing(ChatSendToAll::getP_time).reversed()).collect(Collectors.toList());

    }

    private String getAuthRole(String body, long userID) {
        try {
            JsonNode json = objectMapper.readTree(body);
            if (json.get("kp_id").asLong() == userID) return "ADMIN";
            if (containsID(userID, (ArrayNode) json.get("g_gamers").get("g_keepers"))) return "KP";
            if (containsID(userID, (ArrayNode) json.get("g_gamers").get("g_players"))) return "PL";
            if (containsID(userID, (ArrayNode) json.get("g_gamers").get("g_audiences"))) return "PL";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private boolean containsID(long userID, ArrayNode node) {
        Iterator<JsonNode> iterator = node.elements();
        while (iterator.hasNext()) {
            JsonNode element = iterator.next();
            if (element.get("id").asLong() == userID) return true;
        }
        return false;
    }

    public CommonGameRes publish(RoomInfo roomInfo) throws JsonProcessingException {
        String key = RedisClient.room_list;
        roomInfo.setStatus(RoomStatus.RECRUITING.getStatusValue());
        String edited = JsonUtil.updateKeyForJsonBody(roomInfo.getBody_info(), List.of(new IndexTarget("r_state", 2
                , List.of("r_info"), String.valueOf(RoomStatus.RECRUITING.getStatusValue()))));
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

    public CommonResponse myRoomList(MyRoomListReq req) {
        String key = "";
        switch (req.getType()) {
            case "created":
                key = "g_keepers";
                break;
            case "join":
                key = "g_players";
                break;
            case "onlook":
                key = "g_audiences";
                break;
            default:
                throw new ServiceException("不支持的查询类型");
        }
        String finalKey = key;
        List<RoomInfo> list = new ArrayList<>(redisClient.getAllObjects(RedisClient.room_list, RoomInfo.class));
        list.stream().filter(room -> roomForSomeone(room.getBody_info(), req.getId(), finalKey))
                .skip((long) (req.getPage_cur() - 1) * req.getPage_size()).limit(req.getPage_size()).collect(Collectors.toList());

        return CommonResponse.success(list);

    }

    boolean roomForSomeone(String body_info, long id,String key){
        JsonNode node = null;
        try {
            node = objectMapper.readTree(body_info);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ArrayNode arrayNode = (ArrayNode) node.get("g_gamers").get(key);
        // 使用 elements() 方法获取迭代器
        Iterator<JsonNode> iterator = arrayNode.elements();

        // 遍历迭代器以访问数组中的每个元素
        while (iterator.hasNext()) {
            JsonNode element = iterator.next();
            if (element.get("id").asLong() == id) return true;
        }
        return false;
    }
}
