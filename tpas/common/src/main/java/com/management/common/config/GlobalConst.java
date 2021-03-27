package com.management.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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

    /**
     * 系统目录redis key
     **/
    public static final String SYSTEM_MENUS_KEY = "TPAS::SYSTEM_MENU_LIST";

    /**
     *  系统权限redis key
     **/
    public static final String SYSTEM_PERMISSIONS_KEY = "TPAS::SYSTEM_PERMISSION_LIST";

    /**
     * 文件传输请求content-type
     *
     * @author dude
     * @date 2021/1/31
     **/
    public static final String CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";

    /**
     * 上传图片后缀类型
     *
     * @author dude
     * @date 2021/2/8
     **/
    public static final List<String> IMAGE_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png");

    /**
     * 密码规则：至少6个字符，至少一个数字，至少一个字母
     *
     * @author dude
     * @date 2021/2/9
     **/
    public static final Pattern PASSWORD_FORMAT = Pattern.compile("(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{6,20}$");

}
