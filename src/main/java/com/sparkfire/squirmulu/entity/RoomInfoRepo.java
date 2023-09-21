package com.sparkfire.squirmulu.entity;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface RoomInfoRepo extends ElasticsearchRepository<RoomInfo, String> {
    List<Long> findByKeyWord(String title);
}
