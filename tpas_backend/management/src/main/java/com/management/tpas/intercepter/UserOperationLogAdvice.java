package com.management.tpas.intercepter;

import com.management.common.config.GlobalConst;
import com.management.common.model.BaseResponse;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.dao.UserLogMapper;
import com.management.tpas.entity.UserLog;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.JwtUtil;
import com.management.tpas.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;

/**
 * @author dude
 * @version 1.0
 * @classname UserOperationLogAdvice
 * @description 拦截响应信息, 对于登录验证失败的信息不进行记录
 * @date 2020/8/19
 **/
@RestControllerAdvice
public class UserOperationLogAdvice<T> implements ResponseBodyAdvice<T> {

    private static final Logger log = LoggerFactory.getLogger(UserOperationLogAdvice.class);

    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public T beforeBodyWrite(T body,
                             MethodParameter methodParameter,
                             MediaType mediaType,
                             Class<? extends HttpMessageConverter<?>> aClass,
                             ServerHttpRequest serverHttpRequest,
                             ServerHttpResponse serverHttpResponse) {
        log.info(serverHttpRequest.getClass().toString());
        log.info(serverHttpResponse.getClass().toString());
        log.info(body.getClass().toString());
        if (!(serverHttpRequest instanceof ServletServerHttpRequest)
                || !(serverHttpResponse instanceof ServletServerHttpResponse)
                || !(body instanceof BaseResponse)) {
            return body;
        }
        // 获取请求url、ip、参数信息
        String url = serverHttpRequest.getURI().getPath();
        String ip = serverHttpRequest.getRemoteAddress().toString();
        String param = Utils.getBody(((ServletServerHttpRequest) serverHttpRequest).getServletRequest());

        // 获取响应信息
        Map<String, Object> responseBody = JacksonUtil.object2Map(body);
        String resultCode = responseBody.getOrDefault("code", new Object()).toString();
        String resultMsg = responseBody.getOrDefault("msg", new Object()).toString();

        // 获取用户信息
        List<String> headerList = serverHttpRequest.getHeaders().get(GlobalConst.TOKEN_NAME);
        String token = null;
        if (null != headerList && headerList.size() > 0) {
            token = headerList.get(0);
        }
        UserMsgModel userMsg = null;
        if (null != token) { // 已登录用户请求，由token解析
            userMsg = JwtUtil.getUserMsgByToken(token);
        }
        // 新登录用户，由请求信息解析
        if (userMsg == null) {
            try {
                userMsg = JacksonUtil.json2Object(JacksonUtil.object2Json(responseBody.get("data")), UserMsgModel.class);
            } catch (Exception e) {
                log.error("fail parsing response body: {}", e.getMessage());
                return body;
            }
        }

        if (userMsg == null) {
            return body;
        }
        UserLog userLog = new UserLog(
                ip,
                userMsg.getId(),
                userMsg.getUserType(),
                url,
                param,
                resultCode,
                resultMsg);

        userLogMapper.insert(userLog);

        return body;
    }

}
