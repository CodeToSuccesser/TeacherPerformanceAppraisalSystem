package com.management.tpas.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.intercepter.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dude
 * @version 1.0
 * @classname Utils
 * @description 通用工具类
 * @date 2020/9/4
 **/
public class Utils {

    private final static Logger log = LoggerFactory.getLogger(Utils.class);

    /**
     * @description 获取请求参数
     * @param request 请求
     * @return java.lang.String
     *
     * @author dude
     * @date 2020/9/3
     **/
    public static String getBody(HttpServletRequest request) {
        boolean ipUploadFile = request.getContentType().toLowerCase().contains(
                MediaType.MULTIPART_FORM_DATA_VALUE.toLowerCase());
        if(ipUploadFile){
            return JacksonUtil.object2Json(request.getParameterMap());
        } else {
            return JacksonUtil.object2Json(new RequestWrapper(request).getRequestBody());
        }
    }

    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        if(request instanceof RequestWrapper){
            RequestWrapper requestWrapper = (RequestWrapper) request;
            Map<String, Object> body = JacksonUtil.object2Map(requestWrapper.getRequestBody());
            if (!CollectionUtils.isEmpty(body)) {
                paramMap.putAll(body);
            }
        }
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            paramMap.put(key, request.getParameter(key));
        }

        return paramMap;
    }
}
