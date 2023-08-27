package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.NpcCard;
import com.sparkfire.squirmulu.entity.RoomInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface NpcCardDao {
    @Insert("INSERT INTO npc_card(creator,npc_mods,body_info,p_time,edit_time,c_price) VALUES (#{creator},#{npc_mods},#{body_info},#{p_time},#{edit_time},#{c_price})")
    void insert(NpcCard info);

    @Insert("SELECT creator,npc_mods,body_info,p_time,edit_time,c_price from npc_card WHERE npc_mods=#{npc_mods}")
    NpcCard get(@Param("npc_mods")long npc_mods);

    @Update("UPDATE npc_card SET body_info=#{body_info}, edit_time=#{edit_time} WHERE npc_mods=#{npc_mods}")
    void update(NpcCard info);

    @Insert("DELETE from npc_card WHERE npc_mods=#{npc_mods}")
    void delete(@Param("npc_mods")long npc_mods);
}
