package com.management.common.config;

/**
 * @author dude
 * @version 1.0
 * @classname GlobalConst
 * @description 全局字段配置
 * @date 2020/8/9
 **/
public class GlobalConst {

    /**
     * redis key 前缀
     **/
    public static final String REDIS_KEY_PREFIX = "TPAS::LoginId";

    /**
     * 缓存账号信息 key
     **/
    public static final String USER_MSG_KEY = "userMsgKey";
    public static final String USER_TYPE_KEY = "userType";

    /**
     * redis存储token令牌字段
     **/
    public static final String TOKEN_NAME = "Authorization";

    /**
     * redis过期时间 1hour
     **/
    public static final Long REDIS_TIME_OUT = 3600L;
}
