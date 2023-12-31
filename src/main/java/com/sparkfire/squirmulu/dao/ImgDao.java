package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.Img;
import com.sparkfire.squirmulu.entity.PlayerCard;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImgDao {
    @Insert("INSERT INTO img(file,userID,type,c_time, m_time) VALUES (#{file},#{userID},#{type},#{c_time},#{m_time})")
    void insert(Img img);

    @Delete("DELETE FROM img where file=#{file}")
    void delete(@Param("file") String file);

    @Select("SELECT file FROM img WHERE userID=#{userID} and type=#{type} order by c_time desc")
    List<String> getByIDAndType(@Param("userID") long userID, @Param("type") int type);

    @Select("SELECT userID FROM img WHERE file=#{file}")
    long getUserIDByFileName(@Param("file") String file);
}
