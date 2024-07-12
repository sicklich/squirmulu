package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.CommonFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AudioDao {
    @Insert("INSERT INTO audio(file,userID,type,c_time, m_time) VALUES (#{file},#{userID},#{type},#{c_time},#{m_time})")
    void insert(CommonFile audio);

    @Delete("DELETE FROM audio where file=#{file}")
    void delete(@Param("file") String file);

    @Select("SELECT file FROM audio WHERE userID=#{userID} and type=#{type} order by c_time desc")
    List<String> getByIDAndType(@Param("userID") long userID, @Param("type") int type);

    @Select("SELECT userID FROM audio WHERE file=#{file}")
    long getUserIDByFileName(@Param("file") String file);
}
