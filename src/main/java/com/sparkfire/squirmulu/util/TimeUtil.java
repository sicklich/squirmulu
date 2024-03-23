package com.sparkfire.squirmulu.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static String getMonthFormat(long time) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());

        // 使用 DateTimeFormatter 格式化日期为 "yyyymm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        return dateTime.format(formatter);
    }

    public static long getMonthStartSecond() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取本月第一天的日期
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        // 将本月第一天的日期转换为 ZonedDateTime
        ZonedDateTime firstDayOfMonthZonedDateTime = firstDayOfMonth.atStartOfDay(ZoneId.systemDefault());

        // 将 ZonedDateTime 转换为秒级时间戳
        return firstDayOfMonthZonedDateTime.toEpochSecond();

    }
}