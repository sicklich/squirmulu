package com.sparkfire.squirmulu.dao;

import com.sparkfire.squirmulu.entity.UserCoin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserCoinDao {
    @Insert("INSERT INTO user_coin (id, coin_type, amount, create_time, edit_time) " +
            "VALUES (#{id}, #{coin_type}, #{amount}, #{create_time}, #{edit_time})")
    int insert(UserCoin UserCoin);

    @Delete("DELETE FROM user_coin WHERE id = #{id}")
    int delete(long id);

    @Update("UPDATE user_coin SET coin_type = #{coin_type}, amount = #{amount}, create_time = #{create_time}, edit_time = #{edit_time} WHERE id = #{id}")
    int update(UserCoin UserCoin);

    @Update("UPDATE user_coin SET amount = amount + #{incr}, edit_time = #{time} WHERE id = #{id} AND coin_type = #{coin_type}")
    int updateAmountByCoinType(@Param("id") long id, @Param("coin_type") int coin_type, @Param("incr") int incr,@Param("time") long time);

    @Update("UPDATE ${table} SET amount = amount + #{incr}, edit_time = #{time} WHERE id = #{id} AND coin_type = #{coin_type}")
    int updateAmountByCoinTypeMonthly(@Param("table") String table, @Param("id") long id, @Param("coin_type") int coin_type, @Param("incr") int incr,@Param("time") long time);

    @Update("UPDATE user_coin SET amount = #{amount}, edit_time = #{time} WHERE id = #{id} AND coin_type = #{coin_type}")
    int updateAmountSetByCoinType(@Param("id") long id, @Param("coin_type") int coin_type, @Param("amount") int amount,@Param("time") long time);

    @Update("UPDATE ${table} SET amount = #{amount}, edit_time = #{time} WHERE id = #{id} AND coin_type = #{coin_type}")
    int updateAmountSetByCoinTypeMonthly(@Param("table") String table, @Param("id") long id, @Param("coin_type") int coin_type, @Param("incr") int incr,@Param("time") long time);

    @Select("SELECT * FROM user_coin WHERE id = #{id} AND coin_type = #{coin_type}")
    UserCoin findById(@Param("id")long id, @Param("coin_type") int coin_type);

    @Select("SELECT * FROM ${table} WHERE coin_type = #{coin_type} ORDER BY amount DESC LIMIT 50")
    List<UserCoin> findMonthRank(@Param("table") String table, @Param("coin_type") int coin_type);

    @Select("SELECT * FROM user_coin")
    List<UserCoin> findAll();
}