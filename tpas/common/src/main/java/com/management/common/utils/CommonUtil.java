package com.management.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    static public List<String> parseStringList(String data, String regex) {
        if (StringUtils.isBlank(data)) {
            return Collections.emptyList();
        }
        return Arrays.stream(data.replaceAll(" ", "")
                .split(regex))
                .map(String::trim)
                .filter(it -> !StringUtils.isBlank(it))
                .distinct()
                .collect(Collectors.toList());
    }
}
