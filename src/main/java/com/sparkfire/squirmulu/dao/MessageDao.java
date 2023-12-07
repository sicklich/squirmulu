package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.MessageDB;
import com.sparkfire.squirmulu.entity.PlayerCard;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDao {
    @Insert("INSERT INTO message(id,user_id,type,backend_type,message_body,c_time,status) VALUES (#{id},#{user_id},#{type},#{backend_type},#{message_body},#{c_time},#{status})")
    void insert(MessageDB message);

    @Select("SELECT id,user_id,type,backend_type,message_body,c_time,status from message where type=#{type} and user_id=#{user_id} order by c_time desc limit #{offset},#{page_size}")
    List<MessageDB> getByPage(@Param("user_id")long user_id,@Param("type")int type,@Param("offset")int offset, @Param("page_size")int page_size);

    @Update("UPDATE message SET status=#{status} WHERE id=#{id}")
    void update(@Param("status") int status, @Param("id")long id);

    @Insert("DELETE from card WHERE id=#{id}")
    void delete(@Param("id")long id);
}
