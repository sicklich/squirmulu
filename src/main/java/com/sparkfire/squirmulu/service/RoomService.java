package com.sparkfire.squirmulu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sparkfire.squirmulu.config.RoomListCondition;
import com.sparkfire.squirmulu.config.RoomStatus;
import com.sparkfire.squirmulu.dao.CardDao;
import com.sparkfire.squirmulu.dao.ChatDao;
import com.sparkfire.squirmulu.dao.RoomDao;
import com.sparkfire.squirmulu.entity.*;
import com.sparkfire.squirmulu.entity.request.*;
import com.sparkfire.squirmulu.entity.response.*;
import com.sparkfire.squirmulu.exception.ServiceException;
import com.sparkfire.squirmulu.netty.message.chat.ChatSendToAll;
import com.sparkfire.squirmulu.netty.messageHandler.chat.ChatSendToAllHandler;
import com.sparkfire.squirmulu.util.JsonUtil;
import com.sparkfire.squirmulu.util.RedisClient;
import com.sparkfire.squirmulu.util.SnowflakeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final ConcurrentHashMap<String, ReentrantLock> roomLocks = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RoomDao roomDao;

    @Autowired
    RedisClient redisClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CardDao cardDao;

    @Autowired
    ChatDao chatDao;

//    @Autowired
//    RoomSearchService roomSearchService;

    private static final String body_info = "{" + "  \"id\": \"\"," + "  \"kp_id\": \"\"," + "  \"r_info\": {" + "    \"r_img\": \"\"," + "    \"r_name\": \"\"," + "    \"kp_name\": \"\"," + "    \"c_time\": \"\"," + "    \"g_time\": \"\"," + "    \"pl_cur\": 0," + "    \"pl_max\": 4," + "    \"r_state\": 0," + "    \"r_tags\": [" + "      \"COC7th\"" + "    ]," + "    \"r_des\": \"\"" + "  }," + "  \"g_setting\": {" + "    \"g_type\": \"COC\"," + "    \"g_creator\": \"\"," + "    \"g_mods\": \"\"," + "    \"p_time\": \"\"," + "    \"c_price\": 0" + "  }," + "  \"r_setting\": {" + "    \"r_rule\": {" + "      \"r_acc\": {" + "        \"account\": \"\"," + "        \"password\": \"\"," + "        \"approve_required\": true" + "      }," + "      \"r_card\": {" + "        \"self_modi\": false," + "        \"cross_check\": false" + "      }," + "      \"r_check\": {" + "        \"succ\": 5," + "        \"fail\": 96," + "        \"strict_required\": true" + "      }," + "      \"r_result\": {" + "        \"self_roll\": true," + "        \"edit_forbidden\": false" + "      }," + "      \"r_more\": {" + "        \"statement\": \"\"" + "      }" + "    }" + "  }," + "  \"r_npc\": {" + "    \"npc_list\": [" + "      {" + "        \"npc_setting\": {}," + "        \"npc_info\": {" + "          \"name\": \"\"," + "          \"img\": \"\"," + "          \"type\": \"\"," + "          \"intro\": \"\"" + "        }," + "        \"npc_state\": {" + "          \"hp_cur\": 0," + "          \"mp_cur\": 0," + "          \"damage\": \"\"" + "        }," + "        \"npc_att\": {" + "          \"str\": 0," + "          \"con\": 0," + "          \"siz\": 0," + "          \"dex\": 0," + "          \"app\": 0," + "          \"int\": 0," + "          \"pow\": 0," + "          \"edu\": 0," + "          \"luc\": 0" + "        }," + "        \"npc_extra\": {" + "          \"att\": [" + "            {" + "              \"name\": \"\"," + "              \"value\": 0" + "            }" + "          ]," + "          \"details\": \"\"" + "        }" + "      }" + "    ]" + "  }," + "  \"r_expo\": {" + "    \"expo_map\": {" + "      \"img\": \"\"," + "      \"intro\": \"\"" + "    }," + "    \"expo_scene_cur\": 0," + "    \"expo_scene\": [" + "          {" + "                \"name\": \"\", " + "                \"img\": \"\", " + "                \"bgm\": \"\", " + "                \"des\": \"\", " + "                \"vis\": true  " + "          }" + "     ]" + "  }," + "  \"g_gamers\": {" + "    \"g_keepers\": []," + "    \"g_players\": []," + "    \"g_audiences\": []" + "  }," + "  \"g_record\": {" + "  \"channel\": [" + "           {" + "               \"tips\": \"主频道\"," + "               \"vis_list\": []" + "           }" + "   ]," + "    \"record_list\": []" + "  }," + "  \"g_chat\": {" + "  \"channel\": [" + "           {" + "               \"tips\": \"主频道\"," + "               \"vis_list\": []" + "           }" + "   ]," + "    \"record_list\": []" + "  }," + "  \"g_clue\": {" + "    \"clue_list\": [" + "      {" + "        \"c_a_name\": \"\"," + "        \"c_content\": \"\"," + "        \"vis_status\": false," + "        \"vis_avatar\": []" + "      }" + "    ]" + "  }" + "}";

    public CommonGameRes createRoom(RoomInfo info) {
        long now = System.currentTimeMillis() / 1000;
        long id = SnowflakeGenerator.nextId();
        info.setId(String.valueOf(id));
        info.setCreate_time(now);
        info.setEdit_time(now);
        JsonNode node = null;
        String updatedData = "";
        try {
            node = objectMapper.readTree(body_info);
            ObjectNode objectNode = (ObjectNode) node;
            objectNode.put("kp_id", info.getKp_id());
            ObjectNode r_info = (ObjectNode) objectNode.get("r_info");
            r_info.put("kp_name", info.getKp_name());
            updatedData = objectMapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        info.setBody_info(updatedData);
        processBaseInfo(info);
//        RoomESInfo esInfo = new RoomESInfo();
//        esInfo.setId(info.getId());
//        esInfo.setR_des(info.getR_des());
//        esInfo.setR_name(info.getR_name());
        roomDao.insert(info);
        redisClient.addObject(RedisClient.room_list, String.valueOf(id), info);
        return new CommonGameRes(String.valueOf(id));
//        roomDao.insert(info);
    }

    public CommonGameRes deleteRoom(String id) {
        redisClient.deleteObject(RedisClient.room_list, id);
        roomDao.deleteRoom(Long.parseLong(id));
        return new CommonGameRes(id);
    }

    public void processBaseInfo(RoomInfo info) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(info.getBody_info());
            info.setG_time(node.get("r_info").get("g_time").asLong());
            ((ObjectNode) node).put("id", String.valueOf(info.getId()));
            info.setPwd(node.get("r_setting").get("r_rule").get("r_acc").get("password").asText());
            info.setApprove_required(node.get("r_setting").get("r_rule").get("r_acc").get("approve_required").asBoolean());
            ((ObjectNode) node.get("r_setting").get("r_rule").get("r_acc")).put("account", String.valueOf(info.getId()));
//            ArrayNode arrayNode = (ArrayNode) node.get("g_gamers").get("g_keepers");
//            ObjectNode objectNode = objectMapper.createObjectNode();
//            objectNode.put("id", info.getKp_id());
//            arrayNode.add(objectNode);
            info.setPl_cur(node.get("r_info").get("pl_cur").asInt());
            info.setPl_max(node.get("r_info").get("pl_max").asInt());
            info.setR_name(node.get("r_info").get("r_name").asText());
            info.setR_des(node.get("r_info").get("r_des").asText());
            long kpId = node.get("kp_id").asLong();
            if (kpId != 0) {
                info.setKp_id(node.get("kp_id").asLong());
            }
            info.setR_tags(objectMapper.writeValueAsString(node.get("r_info").get("r_tags")));
            info.setStatus(node.get("r_info").get("r_state").asInt());
            info.setBody_info(node.toString());
        } catch (JsonProcessingException e) {
            return;
        }
    }

    public CommonGameRes updateRoom(IndexBody body) throws JsonProcessingException {
        String key = RedisClient.room_list;
        RoomInfo info = getRoomInfo(body.getId() + "");
        String edited = JsonUtil.updateKeyForJsonBody(info.getBody_info(), body.getTargets());
        info.setBody_info(edited);
        //todo  需要测试 引用部分需要认真对待
        processBaseInfo(info);
        roomDao.update(info);
        redisClient.addObject(key, String.valueOf(info.getId()), info);
        return new CommonGameRes(String.valueOf(info.getId()));
    }

    public CommonGameRes addRoomAttr(IndexBodyForAdd body) {
        String key = RedisClient.room_list;
        RoomInfo info = getRoomInfo(body.getId() + "");
        String edited = JsonUtil.addKeyForJsonBody(info.getBody_info(), body.getTargets());
        info.setBody_info(edited);
        //todo  需要测试 引用部分需要认真对待
        processBaseInfo(info);
        roomDao.update(info);
        redisClient.addObject(key, String.valueOf(info.getId()), info);
        return new CommonGameRes(String.valueOf(info.getId()));
    }

    public int getRoomAllRolesNum(String roomId) throws JsonProcessingException {
        RoomInfo info = getRoomInfo(roomId);
        JsonNode data = objectMapper.readTree(info.getBody_info());
        ArrayNode gKeepers = (ArrayNode) data.path("g_gamers").path("g_keepers");
        return info.getPl_cur() + gKeepers.size() + 1;
    }

    public boolean roomEnough(String roomId) {
        RoomInfo info = getRoomInfo(roomId);
        JsonNode data = null;
        try {
            data = objectMapper.readTree(info.getBody_info());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServiceException("数据异常", -2);
        }
        ArrayNode gKeepers = (ArrayNode) data.path("g_gamers").path("g_keepers");
        return info.getPl_cur() + gKeepers.size() + 1 > info.getPl_max() + 5;
    }

    public boolean playerEnough(String roomId) throws JsonProcessingException {
        RoomInfo info = getRoomInfo(roomId);
        return info.getPl_cur() >= info.getPl_max();
    }

    public void enterRoom(String roomID, String cardID, String userID, String enter_mode) {
        //这里要锁房间查看人数 进行操作
        roomLocks.putIfAbsent(roomID, new ReentrantLock());
        ReentrantLock roomLock = roomLocks.get(roomID);
        try {
            if (roomLock.tryLock(200, TimeUnit.MILLISECONDS)) {
                try {
                    if (!userInRoom(roomID, userID)) {
                        if (!roomEnough(roomID) && !enter_mode.equals("AU")) {
                            updateRoomCard(new RoomCardUpdateReq(roomID, cardID, userID, Arrays.asList(new RoomCardUpdateTarget("join", "g_players"))));
                        } else {
                            updateRoomCard(new RoomCardUpdateReq(roomID, cardID, userID, Arrays.asList(new RoomCardUpdateTarget("join", "g_audiences"))));
                        }
                    }
                } finally {
                    roomLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new ServiceException("服务器繁忙", -1);
        }

    }

    public CommonResponse updateRoomCard(RoomCardUpdateReq req) {
        RoomInfo info = getRoomInfo(req.getId());
        // 解析 JSON 字符串
        try {
            JsonNode data = objectMapper.readTree(info.getBody_info());

            for (RoomCardUpdateTarget target : req.getTargets()) {
                switch (target.getMode()) {
                    case "keep":
                        ArrayNode gKeepers = (ArrayNode) data.path("g_gamers").path("g_keepers");
                        ObjectNode newObject = objectMapper.createObjectNode();
                        newObject.put("id", req.getUser_id());
                        newObject.set("rolecard", objectMapper.readTree(cardDao.getRoleCardByID(Long.parseLong(req.getCard_id()))));
                        gKeepers.add(newObject);

                        // 将修改后的数据转换回 JSON 字符串
                        String updatedJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
                        info.setBody_info(updatedJsonStr);
                        redisClient.addObject(RedisClient.room_list, String.valueOf(info.getId()), info);
                        break;
                    case "join":
                        ArrayNode target_users = (ArrayNode) data.path("g_gamers").path(target.getTarget());
                        ObjectNode targetObject = objectMapper.createObjectNode();
                        targetObject.put("id", req.getUser_id());
                        targetObject.set("rolecard", objectMapper.readTree(cardDao.getRoleCardByID(Long.parseLong(req.getCard_id()))));
                        target_users.add(targetObject);

                        // 将修改后的数据转换回 JSON 字符串
                        ArrayNode gamers = (ArrayNode) data.path("g_gamers").path("g_players");
                        ((ObjectNode) data.get("r_info")).put("pl_cur", gamers.size());
                        info.setPl_cur(gamers.size());
                        String updated = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
                        info.setBody_info(updated);
                        redisClient.addObject(RedisClient.room_list, String.valueOf(info.getId()), info);
                        break;
                    case "kickoff":
                        ArrayNode target_gamers = (ArrayNode) data.path("g_gamers").path(target.getTarget());
                        // 遍历 g_keepers 数组并检查每个元素的 Id 值
                        for (int i = 0; i < target_gamers.size(); i++) {
                            JsonNode keeper = target_gamers.get(i);
                            String id = keeper.path("id").asText();

                            // 如果找到匹配的元素，则将其删除
                            if (req.getUser_id().equals(id)) {
                                target_gamers.remove(i);
                                break;
                            }
                        }
                        gamers = (ArrayNode) data.path("g_gamers").path("g_players");
                        ((ObjectNode) data.get("r_info")).put("pl_cur", gamers.size());
                        info.setPl_cur(gamers.size());
                        String updatedKick = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
                        info.setBody_info(updatedKick);
                        redisClient.addObject(RedisClient.room_list, String.valueOf(info.getId()), info);
                        break;
                    case "change":
                        ArrayNode target_objects = (ArrayNode) data.path("g_gamers").path(target.getTarget());

                        ArrayNode[] objectArrays = new ArrayNode[]{
                                (ArrayNode) data.path("g_gamers").path("g_keepers"),
                                (ArrayNode) data.path("g_gamers").path("g_players"),
                                (ArrayNode) data.path("g_gamers").path("g_audiences")
                        };

                        String[] targets = new String[]{"g_keepers", "g_players", "g_audiences"};

                        boolean find = false;
                        JsonNode targetObj = null;

                        for (int j = 0; j < objectArrays.length && !find; j++) {
                            ArrayNode currentArray = objectArrays[j];
                            for (int i = 0; i < currentArray.size(); i++) {
                                JsonNode obj = currentArray.get(i);
                                String id = obj.path("id").asText();

                                if (req.getUser_id().equals(id)) {
                                    if (target.getTarget().equals(targets[j])) {
                                        throw new Exception();
                                    }
                                    find = true;
                                    targetObj = obj;
                                    currentArray.remove(i);
                                    break;
                                }
                            }
                        }

                        if (!find) {
                            throw new Exception();
                        }

                        target_objects.add(targetObj);

                        // 将修改后的数据转换回 JSON 字符串
                        gamers = (ArrayNode) data.path("g_gamers").path("g_players");
                        ((ObjectNode) data.get("r_info")).put("pl_cur", gamers.size());
                        info.setPl_cur(gamers.size());
                        String updatedData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
                        info.setBody_info(updatedData);
                        redisClient.addObject(RedisClient.room_list, String.valueOf(info.getId()), info);
                        break;
                    default:
                        break;

                }
            }
        } catch (Exception e) {
            throw new ServiceException("数据错误", -2);
        }

//        ArrayNode target_gamers = (ArrayNode) data.path("g_gamers").path("g_players");
//        ((ObjectNode) data.get("r_info")).put("pl_cur", target_gamers.size());
//        info.setPl_cur(target_gamers.size());

        long now = System.currentTimeMillis() / 1000;
        info.setEdit_time(now);
        roomDao.update(info);
        return CommonResponse.success(new RoomCardUpdateRes(true));
    }

    public RoomInfoResIDString getRoomInfo(IndexBody body) throws JsonProcessingException {
        String key = RedisClient.room_list;
        RoomInfo info = getRoomInfo(body.getId() + "");
        List<GameInfoElement> elements = new ArrayList<>();
        if (body.getTargets().isEmpty()) {
            elements.add(new GameInfoElement("game_room", info.getBody_info()));
            return new RoomInfoResIDString(body.getId() + "", getAuthRole(info.getBody_info(), body.getUser_id()), elements);
        }
        for (IndexTarget target : body.getTargets()) {
            String gameInfo = JsonUtil.getByIndexTarget(info.getBody_info(), target);
            elements.add(new GameInfoElement(target.getTarget(), gameInfo));
        }
        return new RoomInfoResIDString(body.getId() + "", getAuthRole(info.getBody_info(), body.getUser_id()), elements);
    }

    public List<ChatSendToAll> getChatList(ChatListReq req) {
        String key = (req.getChat_type() == ChatSendToAllHandler.CHAT ? RedisClient.room_chat_list : RedisClient.room_record_list) + req.getRoom_id();
        long start = req.getNum_cur();
        long end = start + req.getPage_size() - 1;
        List<ChatSendToAll> chats = redisClient.zRevRange(key, start, end, ChatSendToAll.class).stream()
                .sorted(Comparator.comparing(ChatSendToAll::getP_time).reversed()).collect(Collectors.toList());

        if (chats.isEmpty()) {
            RoomInfo info = getRoomInfo(req.getRoom_id() + "");
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(info.getCreate_time()), ZoneId.systemDefault());

            // 使用 DateTimeFormatter 格式化日期为 "yyyymm"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            String formatted = dateTime.format(formatter);
            chats = chatDao.findByPage("chat_" + formatted, req.getRoom_id(), req.getChat_type(), (int) start, req.getPage_size());
            if (!chats.isEmpty()) {
                Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
                for (ChatSendToAll chat : chats) {
                    try {
                        tuples.add(new DefaultTypedTuple<>(objectMapper.writeValueAsString(chat), (double) chat.getP_time()));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
                redisClient.zAddAll(key, tuples);
            }
        }
        return chats;
    }

    public ClearMsgRes clearMsg(ClearMsgReq req) {
        String key = (req.getType() == ChatSendToAllHandler.CHAT ? RedisClient.room_chat_list : RedisClient.room_record_list) + req.getRoom_id();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(getRoomInfo(req.getRoom_id() + "").getCreate_time()), ZoneId.systemDefault());

        // 使用 DateTimeFormatter 格式化日期为 "yyyymm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String formatted = dateTime.format(formatter);
        if (req.getP_channel() == -1) {
            redisClient.zClear(key);
            chatDao.deleteByRoomIdAndType("chat_" + formatted, req.getRoom_id(), req.getType());
            return new ClearMsgRes(0, req.getRoom_id(), req.getType(), req.getP_channel());
        }
        //遍历去删除
        deleteMsgBatches(key, req.getP_channel());
        chatDao.deleteByRoomIdAndTypeAndPChannel("chat_" + formatted, req.getRoom_id(), req.getType(), req.getP_channel());
        return new ClearMsgRes(0, req.getRoom_id(), req.getType(), req.getP_channel());
    }

    public void deleteMsgBatches(String key, int p_channel) {
        long size = redisClient.zSize(key);
        int batchSize = 20;

        for (long start = 0; start < size; start += batchSize) {
            long end = Math.min(start + batchSize - 1, size - 1);
            Set<ChatSendToAll> batch = redisClient.zRange(key, start, end, ChatSendToAll.class);
            // 处理批次中的元素
            for (ChatSendToAll item : batch) {
                if(item.getP_channel() == p_channel){
                    redisClient.zRemove(key, item, ChatSendToAll.class);
                }
            }
        }
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
        RoomInfo room = getRoomInfo(roomInfo.getId());
        room.setStatus(RoomStatus.RECRUITING.getStatusValue());
        logger.info("publish body{}, room id{}, status{}", room.getBody_info(), room.getId(), room.getStatus());
        String edited = JsonUtil.updateKeyForJsonBody(room.getBody_info(), List.of(new IndexTarget("r_state", 2, List.of("r_info"), String.valueOf(RoomStatus.RECRUITING.getStatusValue()), 0)));
        room.setBody_info(edited);
        processBaseInfo(room);
        redisClient.addObject(key, String.valueOf(room.getId()), room);
        logger.info("publish body{}, room id{}, status{}", room.getBody_info(), room.getId(), room.getStatus());
        long now = System.currentTimeMillis() / 1000;
        room.setEdit_time(now);
        roomDao.update(room);
        return new CommonGameRes(String.valueOf(room.getId()));
    }

    public RoomListRes searchRoomList(RoomSearchListReq req) {
        try {
            if (req.getSearch_type().equals("room")) {
                RoomInfo room = getRoomInfo(req.getKey_words());
//                    .filter(room -> room.getStatus() == RoomStatus.RECRUITING.getStatusValue())
                GameListElement gameElement = new GameListElement(room.getId(), room.getPwd(), JsonUtil.get(room.getBody_info(), "r_info"));
                return new RoomListRes(List.of(gameElement));
            }
            //redis 获取 排序
            List<Long> ids = req.getSearch_type().equals("general") ? roomDao.searchByKeyWords(req.getKey_words()) : roomDao.searchByTags(req.getKey_words());
            return new RoomListRes(redisClient.getFields(RedisClient.room_list, ids.stream().map(Object::toString).collect(Collectors.toList()), RoomInfo.class
                            , idString -> roomDao.getByIDs(idString).stream()
                                    .peek(this::processBaseInfo)
                                    .collect(Collectors.toList())
                    ).stream()
//                    .filter(room -> room.getStatus() == RoomStatus.RECRUITING.getStatusValue())
                    .map(roomInfo -> new GameListElement(roomInfo.getId(), roomInfo.getPwd(), JsonUtil.get(roomInfo.getBody_info(), "r_info")))
                    .skip((long) (req.getPage_cur() - 1) * req.getPage_size()).limit(req.getPage_size()).collect(Collectors.toList()));
        } catch (Exception e) {
            return new RoomListRes(new ArrayList<>());
        }

    }

    public RoomListRes getRoomList(RoomListCondition condition, int page_cur, int page_size) {
        //redis 获取 排序
        try {
            return new RoomListRes(redisClient.getAllObjects(RedisClient.room_list, RoomInfo.class
                            , () -> roomDao.getAll().stream().collect(Collectors.toMap(RoomInfo::getId, Function.identity())))
                    .stream().peek(roomInfo -> logger.info("room id{}, status{}", roomInfo.getId(), roomInfo.getStatus()))
                    .peek(this::processBaseInfo)
                    .filter(room -> room.getStatus() == RoomStatus.RECRUITING.getStatusValue())
                    .sorted(condition.getRoomConditionComparator())
                    .filter(room -> condition.getStatusValue() != 2 || room.pwdNeeded() == 0)
                    .map(roomInfo -> new GameListElement(roomInfo.getId(), roomInfo.getPwd(), JsonUtil.get(roomInfo.getBody_info(), "r_info")))
                    .skip((long) page_size * (page_cur - 1)).limit(page_size).collect(Collectors.toList()));
        } catch (Exception e) {
            return new RoomListRes(new ArrayList<>());
        }

    }

    public CommonResponse myRoomList(MyRoomListReq req) {
        List<RoomInfo> list = new ArrayList<>(redisClient.getAllObjects(RedisClient.room_list, RoomInfo.class, () -> roomDao.getAll().stream().collect(Collectors.toMap(RoomInfo::getId, Function.identity()))));
        list = list.stream().filter(room -> roomForSomeone(room.getBody_info(), req.getId(), req.getType()))
                .peek(this::processBaseInfo)
                .map(room -> {
                    String bodyinfo = room.getBody_info();
                    try {
                        JsonNode node = objectMapper.readTree(bodyinfo);
                        if (node.isObject()) {
                            ObjectNode objectNode = (ObjectNode) node;
                            objectNode.retain("r_info"); // 仅保留目标键值对
                            room.setBody_info(objectMapper.writeValueAsString(objectNode));
                        }
                        return room;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).sorted(Comparator.comparing(RoomInfo::getEdit_time).reversed()).skip((long) (req.getPage_cur() - 1) * req.getPage_size()).limit(req.getPage_size()).collect(Collectors.toList());

        return CommonResponse.success(list);

    }

    public boolean needApproved(String roomId) {
        RoomInfo room = getRoomInfo(roomId);
        return room.isApprove_required();
    }

    public RoomInfo getRoomInfo(String roomId) {
        return redisClient.getObjectById(RedisClient.room_list, roomId, RoomInfo.class, roomID -> {
            RoomInfo room = roomDao.getByID(Long.parseLong(roomID));
            processBaseInfo(room);
            return room;
        });
    }

    public boolean userInRoom(String roomId, String userId) {
        RoomInfo room = getRoomInfo(roomId);
        try {
            JsonNode node = objectMapper.readTree(room.getBody_info()).get("g_gamers");
            ArrayNode keepers = (ArrayNode) (node.get("g_keepers"));
            ArrayNode players = (ArrayNode) (node.get("g_players"));
            ArrayNode audiences = (ArrayNode) (node.get("g_audiences"));
            ArrayNode combinedArray = objectMapper.createArrayNode();
            combinedArray.addAll(keepers);
            combinedArray.addAll(players);
            combinedArray.addAll(audiences);

            for (JsonNode element : combinedArray) {
                if (element.get("id").asText().equals(userId)) {
                    return true;
                }
            }
            return false;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    boolean roomForSomeone(String body_info, long id, String type) {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(body_info);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        if (type.equals("created")) {
//            return node.get("kp_id").asLong() == id;
//        }
        String key = "";
        switch (type) {
            case "created":
                return node.get("kp_id").asLong() == id;
            case "join":
                key = "g_players";
                break;
            case "onlook":
                key = "g_audiences";
                break;
            case "host":
                key = "g_keepers";
                break;
            default:
                throw new ServiceException("不支持的查询类型");
        }
        ArrayNode arrayNode = (ArrayNode) node.get("g_gamers").get(key);
        // 使用 elements() 方法获取迭代器
        Iterator<JsonNode> iterator = arrayNode.elements();

        // 遍历迭代器以访问数组中的每个元素
        boolean find = false;
        while (iterator.hasNext()) {
            JsonNode element = iterator.next();
            if (element.get("rolecard").get("a_setting").get("card_user").asLong() == id) {
                find = true;
                break;
            }
        }
        if (!type.equals("host")) {
            return find;
        } else {
            return find || node.get("kp_id").asLong() == id;
        }
    }
}
