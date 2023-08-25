package com.sparkfire.squirmulu.util;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sparkfire.squirmulu.entity.IndexElement;
import com.sparkfire.squirmulu.entity.IndexTarget;

import java.util.List;

public class JsonUtil {
    public static String updateKeyForJsonBody(String json, List<IndexTarget> targets) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        for(IndexTarget target : targets) {
            JsonNode tmpNode = node;
            for (int i = 1; i <= target.getLevel(); i++) {
                if (i == target.getLevel()) {
                    ((ObjectNode) tmpNode).put(target.getTarget(), target.getValue());
                } else {
                    String ele = target.getKeys().get(i);
                    if(tmpNode.isArray()){
                        tmpNode = tmpNode.get(Integer.parseInt(ele));
                    }else{
                        tmpNode = tmpNode.get(ele);
                    }
                }
            }
        }
        return JSON.toJSONString(node);
    }

    public static String updateByKey(String json,String key, String value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        ((ObjectNode) node).put(key, value);
        return JSON.toJSONString(node);
    }

    public static String get(String json,String key) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            return "";
        }
        return node.get(key).asText();
    }
}
