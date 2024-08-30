package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.TrackStay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TrackStayDao {

    // Insert
    @Insert("INSERT INTO track_stay(track_type, event, url, timestamp, user_id, session, duration, remark) VALUES(#{trackType}, #{event}, #{url}, #{timestamp}, #{userId}, #{session}, #{duration}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TrackStay trackStay);

    // Delete
    @Delete("DELETE FROM track_stay WHERE id = #{id}")
    int delete(Long id);

    // Update
    @Update("UPDATE track_stay SET track_type = #{trackType}, event = #{event}, url = #{url}, timestamp = #{timestamp}, user_id = #{userId}, session = #{session}, duration = #{duration}, remark = #{remark} WHERE id = #{id}")
    int update(TrackStay trackStay);

    // Select by id
    @Select("SELECT * FROM track_stay WHERE id = #{id}")
    TrackStay findById(Long id);

    // Select all
    @Select("SELECT * FROM track_stay")
    List<TrackStay> findAll();
}
