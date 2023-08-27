package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.NpcCard;
import com.sparkfire.squirmulu.entity.PlayerCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface CardDao {
    @Insert("INSERT INTO card(id,card_creator,c_time,m_time,body_info) VALUES (#{id},#{card_creator},#{c_time},#{m_time},#{body_info})")
    void insert(PlayerCard info);

    @Insert("SELECT id,card_creator,c_time,m_time,body_info from card WHERE id=#{id}")
    PlayerCard get(@Param("id")long id);

    @Update("UPDATE card SET body_info=#{body_info}, edit_time=#{edit_time} WHERE id=#{id}")
    void update(PlayerCard info);

    @Insert("DELETE from card WHERE id=#{id}")
    void delete(@Param("id")long id);
}
