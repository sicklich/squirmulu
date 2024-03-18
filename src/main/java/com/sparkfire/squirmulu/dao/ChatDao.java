package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.netty.message.chat.ChatSendToAll;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ChatDao {

    @Insert("INSERT INTO ${tableName} (id, user_id, room_id, c_content, p_time, chat_type, c_type, p_channel, a_img, a_name) VALUES (#{chat.id}, #{chat.user_id}, #{chat.room_id}, #{chat.c_content}, #{chat.p_time}, #{chat.chat_type}, #{chat.c_type}, #{chat.p_channel}, #{chat.a_img}, #{chat.a_name})")
    int insert(@Param("tableName") String tableName, @Param("chat") ChatSendToAll chat);

    @Delete("DELETE FROM ${tableName} WHERE id = #{id}")
    int delete(@Param("tableName") String tableName, @Param("id") long id);

    @Delete("DELETE FROM ${tableName} WHERE room_id = #{room_id} AND chat_type = #{chat_type}")
    int deleteByRoomIdAndType(@Param("tableName") String tableName, @Param("room_id") long room_id, @Param("chat_type") int chat_type);

    @Delete("DELETE FROM ${tableName} WHERE room_id = #{room_id} AND chat_type = #{chat_type} and p_channel = #{p_channel}")
    int deleteByRoomIdAndTypeAndPChannel(@Param("tableName") String tableName, @Param("room_id") long room_id, @Param("chat_type") int chat_type, @Param("p_channel") int p_channel);

    @Update("UPDATE ${tableName} SET userId = #{chat.userId}, roomId = #{chat.roomId}, content = #{chat.content}, createTime = #{chat.createTime}, backend_channel = #{chat.backendChannel}, type = #{chat.type}, fe_channel = #{chat.feChannel} WHERE id = #{chat.id}")
    int update(@Param("tableName") String tableName, @Param("chat") ChatSendToAll chat);

    @Select("SELECT * FROM ${tableName} WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user_id"),
            @Result(column = "room_id", property = "room_id"),
            @Result(column = "c_content", property = "c_content"),
            @Result(column = "p_time", property = "p_time"),
            @Result(column = "chat_type", property = "chat_type"),
            @Result(column = "c_type", property = "c_type"),
            @Result(column = "p_channel", property = "p_channel"),
            @Result(column = "a_img", property = "a_img"),
            @Result(column = "a_name", property = "a_name"),
    })
    ChatSendToAll findById(@Param("tableName") String tableName, @Param("id") long id);

    @Select("SELECT * FROM ${tableName} WHERE room_id = #{room_id} and chat_type = #{chat_type} order by p_time desc limit #{offset},#{size}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user_id"),
            @Result(column = "room_id", property = "room_id"),
            @Result(column = "c_content", property = "c_content"),
            @Result(column = "p_time", property = "p_time"),
            @Result(column = "chat_type", property = "chat_type"),
            @Result(column = "c_type", property = "c_type"),
            @Result(column = "p_channel", property = "p_channel"),
            @Result(column = "a_img", property = "a_img"),
            @Result(column = "a_name", property = "a_name"),
    })
    List<ChatSendToAll> findByPage(@Param("tableName") String tableName, @Param("room_id") long room_id, @Param("chat_type")int chat_type, @Param("offset") int offset, @Param("size") int size);

    @Select("SELECT * FROM ${tableName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "user_id"),
            @Result(column = "room_id", property = "room_id"),
            @Result(column = "c_content", property = "c_content"),
            @Result(column = "p_time", property = "p_time"),
            @Result(column = "chat_type", property = "chat_type"),
            @Result(column = "c_type", property = "c_type"),
            @Result(column = "p_channel", property = "p_channel"),
            @Result(column = "a_img", property = "a_img"),
            @Result(column = "a_name", property = "a_name"),
    })
    List<ChatSendToAll> findAll(@Param("tableName") String tableName);
}
