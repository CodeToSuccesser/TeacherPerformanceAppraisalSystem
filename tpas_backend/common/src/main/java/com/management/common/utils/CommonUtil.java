package com.management.common.utils;

import javax.servlet.ServletRequest;

import static com.management.common.config.GlobalConst.CONTENT_TYPE_MULTIPART_FORM_DATA;

/**
 * @author dude
 * @version 1.0
 * @classname CommonUtil
 * @description 全局工具类
 * @date 2021/1/31
 **/
public class CommonUtil {

    static public Boolean requestIsUploadFile(ServletRequest request) {
        if (request != null && request.getContentType() != null) {
            return request.getContentType().contains(CONTENT_TYPE_MULTIPART_FORM_DATA);
        }
        return false;
    }
}
