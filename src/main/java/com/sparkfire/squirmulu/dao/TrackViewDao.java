package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.TrackView;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TrackViewDao {

    @Insert("INSERT INTO track_view(track_type, event, url, timestamp, user_id, session, referrer, remark) VALUES(#{trackType}, #{event}, #{url}, #{timestamp}, #{userId}, #{session}, #{referrer}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TrackView trackView);

    @Delete("DELETE FROM track_view WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE track_view SET track_type = #{trackType}, event = #{event}, url = #{url}, timestamp = #{timestamp}, user_id = #{userId}, session = #{session}, referrer = #{referrer}, remark = #{remark} WHERE id = #{id}")
    int update(TrackView trackView);

    @Select("SELECT * FROM track_view WHERE id = #{id}")
    @Results({
            @Result(property = "trackType",  column = "track_type"),
            @Result(property = "event", column = "event"),
            @Result(property = "url", column = "url"),
            @Result(property = "timestamp", column = "timestamp"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "session", column = "session"),
            @Result(property = "referrer", column = "referrer"),
            @Result(property = "remark", column = "remark")
    })
    TrackView findById(Long id);

    @Select("SELECT * FROM track_view")
    @Results({
            @Result(property = "trackType",  column = "track_type"),
            @Result(property = "event", column = "event"),
            @Result(property = "url", column = "url"),
            @Result(property = "timestamp", column = "timestamp"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "session", column = "session"),
            @Result(property = "referrer", column = "referrer"),
            @Result(property = "remark", column = "remark")
    })
    List<TrackView> findAll();
}