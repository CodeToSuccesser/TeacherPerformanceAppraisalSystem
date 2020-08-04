package com.management.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @author dude
 * @description json转化工具
 * @date 2020/7/16
 **/
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    static {
        // 遇到未知属性是否抛出异常 ，默认是抛出异常的
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //属性为null的不转换
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //如果是空对象的时候,不抛异常
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //设置时间的格式化和时区
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        OBJECT_MAPPER.setTimeZone(TimeZone.getDefault());
    }

    /**
     * 对象转换成json
     *
     * @param obj 对象
     * @return String
     */
    public static <T> String object2Json(T obj) {
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JacksonUtil.object2Json转换异常: {}", e.getMessage());
            throw new BusinessException(ErrorCodeEnum.JSON_CONVERT_ERROR.code, ErrorCodeEnum.JSON_CONVERT_ERROR.msg);
        }
    }

    /**
     * 对象转换成格式化的json
     *
     * @param obj 对象
     * @return String
     **/
    public static <T> String object2JsonPretty(T obj) {
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JacksonUtil.object2JsonPretty转换异常: {}", e.getMessage());
            throw new BusinessException(ErrorCodeEnum.JSON_CONVERT_ERROR.code, ErrorCodeEnum.JSON_CONVERT_ERROR.msg);
        }
    }

    /**
     * 将json转换成对象Class
     *
     * @param src   String类型
     * @param clazz T.Class类型
     * @return T
     */
    public static <T> T json2Object(String src, Class<T> clazz) {
        if (StringUtils.isBlank(src)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(src, clazz);
        } catch (Exception e) {
            log.error("JacksonUtil.json2Object转换异常: {}", e.getMessage());
            throw new BusinessException(ErrorCodeEnum.JSON_CONVERT_ERROR.code, ErrorCodeEnum.JSON_CONVERT_ERROR.msg);
        }
    }

    /**
     * 验证是否json对象
     *
     * @param src 字符串
     * @return Boolean
     */
    static Boolean isJsonObjValid(String src) {
        try {
            return JsonNodeType.OBJECT == OBJECT_MAPPER.readTree(src).getNodeType();
        } catch (Exception e) {
            log.error("JacksonUtil.isJsonObjValid异常: {}", e.getMessage());
            return false;
        }
    }

    /**
     * @param src json串
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description json串转map
     * @author dude
     * @date 2020/7/20
     **/
    static Map<String, Object> json2Map(String src) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(Map.class, String.class, Object.class);
        try {
            return OBJECT_MAPPER.readValue(src, javaType);
        } catch (Exception e) {
            log.error("JacksonUtil.isJsonObjValid异常: {}", e.getMessage());
            throw new BusinessException(ErrorCodeEnum.JSON_CONVERT_ERROR.code, ErrorCodeEnum.JSON_CONVERT_ERROR.msg);
        }
    }

    public static void main(String[] args) {
        log.info(new BaseResponse<Object>(ErrorCodeEnum.OK).toString());
        log.info(object2JsonPretty(new BaseResponse<Object>(ErrorCodeEnum.OK).toString()));
        log.info(Objects.requireNonNull(json2Object("{\"code\":555,\"msg\":\"JSON转换异常\"}", BaseResponse.class)).toString());
        log.info("{}", isJsonObjValid("[{\"a\":1}]"));
        log.info("{}", json2Map("{\"a\":1, \"b\":\"2\"}"));
    }
}
