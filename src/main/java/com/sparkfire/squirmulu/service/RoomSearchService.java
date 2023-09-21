package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.entity.RoomInfo;
import com.sparkfire.squirmulu.entity.RoomInfoRepo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoomSearchService {
    @Autowired
    private RoomInfoRepo roomInfoRepo;

    public List<RoomInfo> search(String keyword) {
        // 使用Elasticsearch的查询DSL进行搜索
        // 这里举例使用QueryBuilder进行简单的全文搜索
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(keyword);
        Iterable<RoomInfo> searchResult = roomInfoRepo.search(queryBuilder);

        // 将Iterable结果转换为List
        List<RoomInfo> resultList = new ArrayList<>();
        searchResult.forEach(resultList::add);

        return resultList;
    }
}
