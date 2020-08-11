package com.management.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author dude
 * @version 1.0
 * @classname JwtConfig
 * @description jwt令牌配置
 * @date 2020/8/9
 **/
@Configuration
public class JwtConfig {

    @Value("${jwt.expire-time}")
    public static final int EXPIRE_TIME = 0;// 有效时间（hour）
    @Value("${jwt.jwt-secret}")
    public static final String JWT_SECRET = "JwtSECRET";
}
