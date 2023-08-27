package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.constant.SecurityConstants;
import com.sparkfire.squirmulu.dto.LoginUser;
import com.sparkfire.squirmulu.utils.IdUtils;
import com.sparkfire.squirmulu.utils.JwtUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * token Service
 */
@Component
public class TokenService
{

    private final static long expireTime = 720;

    /**
     * 创建令牌 根据loginUser  创建TOKEN
     */
    public Map<String, Object> createToken(LoginUser loginUser)
    {
        String token = IdUtils.fastUUID();
        String loginName = loginUser.getLoginName();
        loginUser.setToken(token);
        loginUser.setLoginName(loginName);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, loginName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }
}
