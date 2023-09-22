package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.entity.RoomESInfo;
import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.entity.RoomInfoRepo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.MoreLikeThisQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class RoomSearchService {
    @Autowired
    private RoomInfoRepo roomInfoRepo;

    public List<Long> searchByKeyword(String keyword) {
        return roomInfoRepo.findRoomESInfoByR_desOrR_name(keyword);
    }

    public void save(RoomESInfo info) {
        roomInfoRepo.save(info);
    }
}
