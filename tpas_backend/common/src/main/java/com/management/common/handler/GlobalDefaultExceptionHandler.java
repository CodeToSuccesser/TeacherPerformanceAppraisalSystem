package com.management.common.handler;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.common.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dude
 * @description 全局异常处理
 * @date 2020/7/16
 **/
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    /**
     * @param e 业务异常
     * @return String
     * @description 业务异常处理
     * @author dude
     * @date 2020/7/20
     **/
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public String businessExceptionHandler(BusinessException e) {
        log.error("业务异常==> code: {}, msg: {}", e.code, e.code);
        return JacksonUtil.object2Json(new BaseResponse<>(e.code, e.msg));
    }

    /**
     * @param e 运行异常
     * @return String
     * @description 运行异常处理
     * @author dude
     * @date 2020/7/20
     **/
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public String runtimeExceptionHandler(RuntimeException e) {
        log.error("业务异常==> Exception: {}", e.getMessage());
        return JacksonUtil.object2Json(new BaseResponse<>(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg));
    }

    /**
     * @param e 异常
     * @return String
     * @description 通用异常处理
     * @author dude
     * @date 2020/7/20
     **/
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String defaultExceptionHandler(Exception e) {
        log.error("业务异常==> Exception: {}", e.getMessage());
        return JacksonUtil.object2Json(new BaseResponse<>(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg));
    }
}
