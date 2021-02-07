package com.management.tpas.intercepter;

import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.JwtUtil;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.management.common.utils.CommonUtil.requestIsUploadFile;

/**
 * @author dude
 * @version 1.0
 * @classname RequestFilter
 * @description 拦截所有请求过滤器，并将请求类型是HttpServletRequest类型的请求替换为
 * 自定义RequestWrapper，用于拦截器获取请求参数
 * @date 2020/9/4
 **/
@WebFilter(urlPatterns = {"/*"}, filterName = "requestBodyFilter")
public class RequestBodyFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestBodyFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        Boolean isUploadFile = requestIsUploadFile(servletRequest);
        if (servletRequest instanceof HttpServletRequest && !isUploadFile) {
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        }

        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

}
