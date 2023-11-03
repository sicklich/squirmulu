package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.Img;
import com.sparkfire.squirmulu.entity.PlayerCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImgDao {
    @Insert("INSERT INTO img(file,userID,type,c_time, m_time) VALUES (#{file},#{userID},#{type},#{c_time},#{m_time})")
    void insert(Img img);

    @Select("SELECT file FROM img WHERE userID=#{userID} and type=#{type}")
    List<String> getByIDAndType(@Param("userID") long userID, @Param("type") int type);
}
