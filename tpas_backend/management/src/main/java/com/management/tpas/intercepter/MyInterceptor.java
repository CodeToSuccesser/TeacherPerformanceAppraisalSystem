package com.management.tpas.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.management.tpas.dao.UserLogMapper;
import com.management.tpas.entity.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author pwh
 * @create 2020.08.03
 */
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private UserLogMapper mapper;
    @Autowired
    private UserLog userLog;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String remoteAddr = request.getRemoteAddr();
        String userId = request.getParameter("userId");
        String userType = request.getParameter("userType");
        String requestURL = request.getRequestURL().toString();
        String param = request.getQueryString().toString();
        String date = request.getHeader("Date");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localdate = format.parse(date);
        userLog.setIp(remoteAddr);
        userLog.setCreateTime(localdate);
        userLog.setParam(param);
        userLog.setUserId(Long.parseLong(userId));
        userLog.setUserType(Integer.parseInt(userType));
        userLog.setUrl(requestURL);


        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        int status = response.getStatus();
        String warning = response.getHeader("Warning");
        userLog.setResultCode(String.valueOf(status));
        userLog.setResultMsg(warning);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        mapper.insert(userLog);
    }
}