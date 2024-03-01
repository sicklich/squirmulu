package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.NpcCard;
import com.sparkfire.squirmulu.entity.PlayerCard;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface CardDao {
    @Insert("INSERT INTO card(id,card_creator,card_user,c_time,m_time,role_card) VALUES (#{id},#{card_creator},#{card_user},#{c_time},#{m_time},#{role_card})")
    void insert(PlayerCard info);

    @Select("SELECT id,card_creator,card_user,c_time,m_time,role_card from card WHERE id=#{id}")
    PlayerCard get(@Param("id")long id);

    @Select("SELECT id,card_creator,card_user,c_time,m_time,role_card from card WHERE card_user=#{card_user}")
    List<PlayerCard> getByCardUser(@Param("card_user")long card_user);

    @Select("SELECT role_card from card WHERE id=#{id}")
    String getRoleCardByID(@Param("id")long id);

    @Select("SELECT id,card_creator,card_user,c_time,m_time,role_card from card")
    List<PlayerCard> getAll();

    @Update("UPDATE card SET role_card=#{role_card}, m_time=#{m_time} WHERE id=#{id}")
    void update(PlayerCard info);

    @Update("UPDATE card SET card_user=#{card_user} WHERE id=#{id}")
    void updateCardUser(PlayerCard info);

    @Insert("DELETE from card WHERE id=#{id}")
    void delete(@Param("id")long id);
}
