package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.RoomInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface RoomDao {
    @Insert("INSERT INTO room(id,kp_id,body_info,create_time,edit_time) VALUES (#{id},#{kp_id},#{body_info},#{create_time},#{edit_time})")
    void insert(RoomInfo info);

    @Update("UPDATE room SET body_info=#{body_info}, edit_time=#{edit_time} WHERE id=#{id}")
    void update(RoomInfo info);
}
