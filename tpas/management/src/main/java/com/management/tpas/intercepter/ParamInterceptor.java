package com.management.tpas.intercepter;

import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.JwtUtil;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.management.common.config.GlobalConst.TOKEN_NAME;

/**
 * @author dude
 * @version 1.0
 * @classname ParamInterceptor
 * @description request请求参数畜类拦截器
 * @date 2021/2/7
 **/
@Component
public class ParamInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不是controller接口时放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 存储请求用户信息
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            LOGGER.warn("can not get request token from 'Authorization', regard as system request");
            UserMsgModel userMsgModel = new UserMsgModel();
            userMsgModel.setUserName("sys");
            UserUtil.setUserMsg(userMsgModel);
        } else {
            UserUtil.setUserMsg(JwtUtil.getUserMsgByToken(token));
        }

        return true;
    }
}
