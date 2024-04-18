package com.sparkfire.squirmulu.util;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sparkfire.squirmulu.entity.IndexElement;
import com.sparkfire.squirmulu.entity.IndexTarget;
import com.sparkfire.squirmulu.entity.IndexTargetForAdd;
import com.sparkfire.squirmulu.entity.UpdateKeyRes;

import java.util.List;

public class JsonUtil {
    public static UpdateKeyRes updateKeyForJsonBody(String json, List<IndexTarget> targets, long userId) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int idx = 0;
        for (IndexTarget target : targets) {
            JsonNode jsonObject;
            try {
                jsonObject = mapper.readTree(target.getValue());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            JsonNode tmpNode = node;
            for (int i = 1; i <= target.getLevel(); i++) {
                if (i == target.getLevel()) {
                    if (jsonObject != null) {
                        ((ObjectNode) tmpNode).set(target.getTarget(), jsonObject);
                    } else {
                        ((ObjectNode) tmpNode).put(target.getTarget(), target.getValue());
                    }
                } else {
                    String ele = target.getKeys().get(i - 1);
                    if (tmpNode.isArray()) {
                        if (ele.equals("g_players") && userId != 0) {
                            //那么这里是要去找对应的userID而不是根据索引找
                            //遍历一下tmpNode
                            int index = 0;
                            for (JsonNode arrayNode : tmpNode) {
                                if (arrayNode.get("id").asLong() == userId) {
                                    tmpNode = arrayNode;
                                    idx = index;
                                    break;
                                }
                                index++;
                            }
                        } else {
                            tmpNode = tmpNode.get(Integer.parseInt(ele));
                        }
                    } else {
                        tmpNode = tmpNode.get(ele);
                    }
                }
            }
        }
        return new UpdateKeyRes(JSON.toJSONString(node), idx);
    }

    public static String addKeyForJsonBody(String json, List<IndexTargetForAdd> targets) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        for (IndexTargetForAdd target : targets) {
            JsonNode jsonObject;
            try {
                jsonObject = mapper.readTree(target.getValue());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            JsonNode tmpNode = node;
            for (int i = 1; i <= target.getLevel(); i++) {
                System.out.println("level:" + target.getLevel() + " i:" + i);
                if (i == target.getLevel()) {
                    if (jsonObject != null) {
                        if (tmpNode.isArray()) {
                            System.out.println("size: " + tmpNode.size() + "target: " + target.getTarget());
                            ((ObjectNode) tmpNode.get(Integer.parseInt(target.getTarget()))).set(target.getName(), jsonObject);
                        } else {
                            ((ObjectNode) tmpNode.get(target.getTarget())).set(target.getName(), jsonObject);
                        }
                    } else {
                        if (tmpNode.isArray()) {
                            ((ObjectNode) tmpNode.get(Integer.parseInt(target.getTarget()))).put(target.getName(), target.getValue());
                        } else {
                            ((ObjectNode) tmpNode.get(target.getTarget())).put(target.getName(), target.getValue());
                        }
                    }
                } else {
                    String ele = target.getKeys().get(i - 1);
                    System.out.println("ele: " + ele);
                    if (tmpNode.isArray()) {
                        tmpNode = tmpNode.get(Integer.parseInt(ele));
                    } else {
                        tmpNode = tmpNode.get(ele);
                    }
                }
            }
        }
        return JSON.toJSONString(node);
    }

    public static String updateByKey(String json, String key, String value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        ((ObjectNode) node).put(key, value);
        return JSON.toJSONString(node);
    }

    public static String getByIndexTarget(String json, IndexTarget target) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        for (int i = 1; i <= target.getLevel(); i++) {
            if (i == target.getLevel()) {
                return node.get(target.getTarget()).toString();
            } else {
                String ele = target.getKeys().get(i - 1);
                if (node.isArray()) {
                    node = node.get(Integer.parseInt(ele));
                } else {
                    node = node.get(ele);
                }
            }
        }
        return "";

    }

    public static String get(String json, String key) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            return "";
        }
        return node.get(key).toString();
    }
}
