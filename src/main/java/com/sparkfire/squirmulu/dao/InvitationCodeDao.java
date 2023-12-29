package com.sparkfire.squirmulu.dao;

import org.apache.ibatis.annotations.*;
import com.example.demo.entity.InvitationCode;

@Mapper
public interface InvitationCodeDao {

    @Select("SELECT * FROM invitation_codes WHERE code = #{code}")
    InvitationCode findByCode(@Param("code") String code);

    @Insert("INSERT INTO invitation_codes (code, is_used, user_id, created_at, used_at) VALUES (#{code}, #{isUsed}, #{userId}, #{createdAt}, #{usedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(InvitationCode invitationCode);

    @Update("UPDATE invitation_codes SET is_used = #{isUsed}, user_id = #{userId}, used_at = #{usedAt} WHERE code = #{code}")
    int update(InvitationCode invitationCode);
}
