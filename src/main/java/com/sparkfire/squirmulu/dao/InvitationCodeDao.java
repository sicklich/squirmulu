package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.InvitationCode;
import org.apache.ibatis.annotations.*;


@Mapper
public interface InvitationCodeDao {

    @Select("SELECT code,is_used,user_id,create_time,use_time FROM invitation_code WHERE code = #{code}")
    @Results({
            @Result(column = "code", property = "code"),
            @Result(column = "is_used", property = "is_used"),
            @Result(column = "user_id", property = "user_id"),
            @Result(column = "create_time", property = "create_time"),
            @Result(column = "use_time", property = "use_time")
    })
    InvitationCode findByCode(@Param("code") String code);

    @Insert("INSERT INTO invitation_code (code, is_used, user_id, created_time, used_time) VALUES (#{code}, #{is_used}, #{user_id}, #{created_time}, #{use_time})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InvitationCode invitationCode);

    @Update("UPDATE invitation_code SET is_used = #{is_used}, user_id = #{user_id}, use_time = #{use_time} WHERE code = #{code}")
    int update(InvitationCode invitationCode);
}
