package com.sparkfire.squirmulu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RedisClient {

    public static final String room_list = "ROOM_LIST";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // 添加对象到Redis的hash中
    public <T> void addObject(String key, String field, T object) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        try {
            hashOperations.put(key, field, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 从Redis的hash中获取单个对象
    public <T> T getObjectById(String key, String field, Class<T> clazz) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String objectJson = (String) hashOperations.get(key, field);
        try {
            return objectMapper.readValue(objectJson, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取整个对象列表
    public <T> List<T> getAllObjects(String key, Class<T> clazz) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Map<Object, Object> objectMap = hashOperations.entries(key);
        return objectMap.values().stream()
                .map(value -> {
                    try {
                        return objectMapper.readValue((String) value, clazz);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }



// 获取zset中的所有元素，并将其转换为特定类型的对象
    public <T> List<T> getAllElements(String key, Class<T> clazz) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        Set<Object> elements = zSetOperations.range(key, 0, -1);
        if(null == elements) return new ArrayList<>();

        // 将JSON字符串转换为特定类型的对象
        try {
            return elements.stream()
                    .map(element -> {
                        try {
                            return objectMapper.readValue(element.toString(), clazz);
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

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getString(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? "" : (String) value;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 添加其他基本操作，如列表、集合、哈希等
    // 添加元素到zset
    public void ZAdd(String key, Object value, long score) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, value, (double)score);
    }

    // 从zset中移除元素
    public void ZRemove(String key, Object value) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.remove(key, value);
    }

    // 根据分数范围查询zset中的元素
    public Set<Object> ZRangeByScore(String key, double minScore, double maxScore) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.rangeByScore(key, minScore, maxScore);
    }

    // 获取zset中元素的分数
    public Double getScore(String key, Object value) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.score(key, value);
    }

    // 获取zset中的元素数量
    public Long getZSetSize(String key) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.size(key);
    }

    // 根据索引范围查询zset中的元素
    public Set<Object> ZRange(String key, long start, long end) {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.range(key, start, end);
    }
}
