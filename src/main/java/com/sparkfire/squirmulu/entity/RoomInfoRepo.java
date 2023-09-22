package com.sparkfire.squirmulu.entity;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface RoomInfoRepo extends ElasticsearchRepository<RoomESInfo, String> {
    List<Long> findRoomESInfoByR_desOrR_name(String keyword);
}
