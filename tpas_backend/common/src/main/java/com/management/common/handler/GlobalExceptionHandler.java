package com.management.common.handler;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description 全局异常处理
 **/

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(
        BusinessException e) {
        log.error("Exception ==> ", e);
        return new BaseResponse<>(e.code, e.msg);
    }

    /**
     * 通用异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse<?> defaultExceptionHandler(Exception e) {
        log.error("Exception ==> {} ", e.getMessage());
        return new BaseResponse<>(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
    }

}
