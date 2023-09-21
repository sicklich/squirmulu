package com.sparkfire.squirmulu.service;

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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomSearchService {
    @Autowired
    private RoomInfoRepo roomInfoRepo;

    public List<String> searchByKeyword(String keyword) {
        QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(keyword)
                .field("field1")
                .field("field2");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQueryBuilder)
                .build();

        SearchHits<RoomInfo> searchHits = roomInfoRepo.searchSimilar(searchQuery);
        return searchHits.get()
                .map(SearchHit::getId)
                .collect(Collectors.toList());
    }
}
