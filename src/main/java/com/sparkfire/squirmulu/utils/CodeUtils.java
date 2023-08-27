package com.sparkfire.squirmulu.utils;

import java.util.Random;

/**
 * 验证码生成工具类
 */
public class CodeUtils {

    /**
     * 生成随机验证码
     *
     * @return 验证码
     */
    public static String generateCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);
            code.append(num);
        }
        return code.toString();
    }
}

