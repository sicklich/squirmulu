package com.sparkfire.squirmulu.entity;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public class RoomInfoRepo implements ElasticsearchRepository<RoomInfo, String> {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Override
    public Page<RoomInfo> searchSimilar(RoomInfo entity, String[] fields, Pageable pageable) {
        MoreLikeThisQueryBuilder moreLikeThisQueryBuilder = QueryBuilders.moreLikeThisQuery(fields)
                .likeTexts(entity.getR_name())
                .minTermFreq(1)
                .minDocFreq(1);

        // 使用NativeSearchQueryBuilder构建查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(moreLikeThisQueryBuilder)
                .withPageable(pageable)
                .build();

        // 使用ElasticsearchOperations执行查询
        Page<RoomInfo> searchResult = elasticsearchOperations.queryForPage(searchQuery, RoomInfo.class);

        return searchResult;
    }

    @Override
    public Iterable<RoomInfo> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<RoomInfo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RoomInfo> S save(S entity) {
        return null;
    }

    @Override
    public <S extends RoomInfo> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<RoomInfo> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<RoomInfo> findAll() {
        return null;
    }

    @Override
    public Iterable<RoomInfo> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(RoomInfo entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends RoomInfo> entities) {

    }

    @Override
    public void deleteAll() {

    }

    // 自定义查询方法，如果需要
}
