package com.sparkfire.squirmulu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class RedisClient {

    public static final String room_list = "ROOM_LIST";

    public static final String room_chat_list = "ROOM_CHAT_LIST_";

    public static final String room_record_list = "ROOM_RECORD_LIST_";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // 添加对象到Redis的hash中
    public <T> void addObject(String key, String field, T object) {
        try {
            redisTemplate.opsForHash().put(key, field, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void deleteObject(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    // 从Redis的hash中获取单个对象
    public <T> T getObjectById(String key, String field, Class<T> clazz, Function<String, T> f) {
        String objectJson = (String) redisTemplate.opsForHash().get(key, field);
        try {
            if(null == objectJson){
                return f.apply(field);
            }
            return objectMapper.readValue(objectJson, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取整个对象列表
    public <T> List<T> getAllObjects(String key, Class<T> clazz, Supplier<List<T>> s) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        Map<String, String> objectMap = hashOperations.entries(key);
        if(objectMap.isEmpty() || objectMap.values().stream().allMatch(Objects::isNull)){
            return s.get();
        }
        return objectMap.values().stream()
                .map(value -> {
                    try {
                        return objectMapper.readValue(value, clazz);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    // 获取整个对象列表
    public <T> List<T> getFields(String key, List<String> fields, Class<T> clazz, Function<String, List<T>> f) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        List<String> list = hashOperations.multiGet(key, fields);
        if(list.stream().allMatch(Objects::isNull)){
            return f.apply(fields.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        return list.stream()
                .filter(Objects::nonNull)
                .map(value -> {
                    try {
                        return objectMapper.readValue(value, clazz);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }



// 获取zset中的所有元素，并将其转换为特定类型的对象
    public <T> List<T> getAllElements(String key, Class<T> clazz) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<String> elements = zSetOperations.range(key, 0, -1);
        if(null == elements) return new ArrayList<>();

        // 将JSON字符串转换为特定类型的对象
        try {
            return elements.stream()
                    .map(element -> {
                        try {
                            return objectMapper.readValue(element, clazz);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            if (e.getCause() instanceof IOException) {
                return new ArrayList<>(); // 返回空列表
            } else {
                throw e;
            }
        }
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? "" : (String) value;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public <T> Boolean zAdd(String key, T value, double score, Class<T> clazz) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            return redisTemplate.opsForZSet().add(key, jsonValue, score);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing value to JSON", e);
        }
    }

    public <T> Long zRemove(String key, T value, Class<T> clazz) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            return redisTemplate.opsForZSet().remove(key, jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing value to JSON", e);
        }
    }

    public <T> Set<T> zRange(String key, long start, long end, Class<T> clazz) {
        ZSetOperations<String, String> zSetOp = redisTemplate.opsForZSet();
        Set<String> jsonStringSet = redisTemplate.opsForZSet().range(key, start, end);
        return deserializeJsonSet(jsonStringSet, clazz);
    }

    public <T> Set<T> zRevRange(String key, long start, long end, Class<T> clazz) {
        ZSetOperations<String, String> zSetOp = redisTemplate.opsForZSet();
        Set<String> jsonStringSet = redisTemplate.opsForZSet().reverseRange(key, start, end);
        return deserializeJsonSet(jsonStringSet, clazz);
    }

    public <T> Set<T> zRangeByScore(String key, double minScore, double maxScore, Class<T> clazz) {
        Set<String> jsonStringSet = redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
        return deserializeJsonSet(jsonStringSet, clazz);
    }

    private <T> Set<T> deserializeJsonSet(Set<String> jsonStringSet, Class<T> clazz) {
        Set<T> resultSet = new HashSet<>();
        for (String jsonString : jsonStringSet) {
            try {
                T value = objectMapper.readValue(jsonString, clazz);
                resultSet.add(value);
            } catch (IOException e) {
                throw new RuntimeException("Error deserializing JSON", e);
            }
        }
        return resultSet;
    }

    public <T> Set<String> zRangeByScore(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
    }

    public <T> Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, minScore, maxScore);
    }

    public <T> Long rank(String key, T value, Class<T> clazz) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            return redisTemplate.opsForZSet().rank(key, jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing value to JSON", e);
        }
    }

    public <T> Double score(String key, T value, Class<T> clazz) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            return redisTemplate.opsForZSet().score(key, jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing value to JSON", e);
        }
    }

    public <T> T deserializeJson(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing JSON", e);
        }
    }
}
