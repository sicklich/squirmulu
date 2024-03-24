package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.WxTrade;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface WxTradeDao {
    @Insert("INSERT INTO wx_trade (trade_no, status, user_id, total, description, create_time, edit_time) " +
            "VALUES (#{trade_no}, #{status}, #{user_id}, #{total}, #{description}, #{create_time}, #{edit_time})")
    int insert(WxTrade wx_trade);

    @Delete("DELETE FROM wx_trade WHERE trade_no = #{trade_no}")
    int delete(long trade_no);

    @Update("UPDATE wx_trade SET status = #{status}, user_id = #{user_id}, total = #{total}, description = #{description}, create_time = #{create_time}, edit_time = #{edit_time} WHERE trade_no = #{trade_no}")
    int update(WxTrade wx_trade);

    @Update("UPDATE wx_trade SET status = #{status}, edit_time = #{edit_time} WHERE trade_no = #{trade_no}")
    int updateStatus(WxTrade wx_trade);

    @Select("SELECT * FROM wx_trade WHERE trade_no = #{trade_no}")
    @Results({
            @Result(property = "trade_no", column = "trade_no"),
            @Result(property = "status", column = "status"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "total", column = "total"),
            @Result(property = "description", column = "description"),
            @Result(property = "create_time", column = "create_time"),
            @Result(property = "edit_time", column = "edit_time")
    })
    WxTrade findByTradeNo(long trade_no);

    @Select("SELECT * FROM wx_trade")
    List<WxTrade> findAll();
}