package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.RoomDBInfo;
import com.sparkfire.squirmulu.entity.RoomInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface RoomDao {
    @Insert("INSERT INTO room(id,kp_id,body_info,create_time,edit_time,r_name,r_des,r_tags) VALUES (#{id},#{kp_id},#{body_info},#{create_time},#{edit_time},#{r_name},#{r_des},#{r_tags})")
    void insert(RoomInfo info);

    @Update("UPDATE room SET body_info=#{body_info},r_name=#{r_name},r_des=#{r_des},r_tags=#{r_tags}, edit_time=#{edit_time} WHERE id=#{id}")
    void update(RoomInfo info);

    @Select("SELECT id,kp_id,body_info,create_time,edit_time,r_name,r_des,r_tags from room WHERE id=#{id}")
    RoomInfo getByID(@Param("id") long id);

    @Select("SELECT id,kp_id,body_info,create_time,edit_time,r_name,r_des,r_tags from room WHERE id in (${ids})")
    List<RoomDBInfo> getByIDs(@Param("ids") String ids);

    @Select("SELECT id,kp_id,body_info,create_time,edit_time,r_name,r_des,r_tags from room")
    List<RoomInfo> getAll();

    @Delete("DELETE from room WHERE id=#{id}")
    int deleteRoom(@Param("id") long id);

    @Select("SELECT id from room WHERE r_name like '%${keyWord}%' or r_des like '%${keyWord}%'")
    List<Long> searchByKeyWords(@Param("keyWord") String keyWord);

    @Select("SELECT id from room WHERE r_tags like '%${keyWord}%'")
    List<Long> searchByTags(@Param("keyWord") String keyWord);
}
