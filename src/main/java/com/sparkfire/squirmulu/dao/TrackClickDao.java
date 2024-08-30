package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.TrackClick;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrackClickDao {

    // Insert
    @Insert("INSERT INTO track_click(track_type, event, url, timestamp, user_id, session, element_id, element_text, remark) VALUES(#{trackType}, #{event}, #{url}, #{timestamp}, #{userId}, #{session}, #{elementId}, #{elementText}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TrackClick trackClick);

    // Delete
    @Delete("DELETE FROM track_click WHERE id = #{id}")
    int delete(Long id);

    // Update
    @Update("UPDATE track_click SET track_type = #{trackType}, event = #{event}, url = #{url}, timestamp = #{timestamp}, user_id = #{userId}, session = #{session}, element_id = #{elementId}, element_text = #{elementText}, remark = #{remark} WHERE id = #{id}")
    int update(TrackClick trackClick);

    // Select by id
    @Select("SELECT * FROM track_click WHERE id = #{id}")
    TrackClick findById(Long id);

    // Select all
    @Select("SELECT * FROM track_click")
    List<TrackClick> findAll();
}