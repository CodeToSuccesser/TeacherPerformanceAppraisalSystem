package com.management.tpas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dude
 * @version 1.0
 * @classname JwtConfig
 * @description jwt令牌配置
 * @date 2020/8/9
 **/
@Component
public class JwtConfig {

    public static int EXPIRE_TIME = 0;// 有效时间（hour）

    public static String JWT_SECRET = "JwtSECRET";

    @Value("${jwt.expire-time}")
    public void setExpireTime(int expireTime) {
        JwtConfig.EXPIRE_TIME = expireTime;
    }

    @Value("${jwt.jwt-secret}")
    public void setJwtSecret(String jwtSecret) {
        JwtConfig.JWT_SECRET = jwtSecret;
    }
}
