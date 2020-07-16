package com.management.common.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dude
 * @description 全局异常处理
 * @date 2020/7/16
 **/
@RestControllerAdvice public class GlobalDefaultExceptionHandler {

    /**
     * @param request 请求
     * @param e       错误
     * @return
     */
    @ExceptionHandler(value = Exception.class) public String defaultExceptionHandler(HttpServletRequest request,
        Exception e) {
        // TODO
        return null;
    }
}
