package com.management.tpas.intercepter;

import com.management.common.config.GlobalConst;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.enums.SystemPermissionControlTypeEnum;
import com.management.tpas.model.SystemPermissionModel;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.SystemPermissionService;
import com.management.tpas.utils.JwtUtil;
import com.management.tpas.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author dude
 * @version 1.0
 * @classname LoginInterceptor
 * @description 登录拦截器，放行已登录用户的请求
 * @date 2020/8/11
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, UserMsgModel> redisTemplate;

    @Autowired
    private SystemPermissionService permissionService;

    /**
     * @description 请求处理之前对令牌进行校验
     * @param request 请求对象
    * @param response 响应对象
    * @param handler 处理对象
     * @return boolean
     *
     * @author dude
     * @date 2020/8/11
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不是controller接口时放行
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 登录检查
        // 请求头部检查
        String authorization = request.getHeader(GlobalConst.TOKEN_NAME);
        if(null == authorization){ // 请求缺少令牌, 抛异常
            throw new BusinessException(ErrorCodeEnum.LOGIN_TIME_OUT.code, ErrorCodeEnum.LOGIN_TIME_OUT.msg);
        }
        // token解析
        String headerJwtSub = JwtUtil.parseJWT(authorization).getSubject();
        if(null == headerJwtSub){ // 判断header没有传authorization
            throw new BusinessException(ErrorCodeEnum.TOKEN_ERROR.code, ErrorCodeEnum.TOKEN_ERROR.msg);
        }
        // 解析用户信息
        UserMsgModel userMsgModel = JacksonUtil.json2Object(headerJwtSub, UserMsgModel.class);
        if(null == userMsgModel){
            throw new BusinessException(ErrorCodeEnum.LOGIN_TIME_OUT.code, ErrorCodeEnum.LOGIN_TIME_OUT.msg);
        }
        // 根据用户信息生成redis存储的key
        String key = GlobalConst.REDIS_KEY_PREFIX + userMsgModel.getUserType() + userMsgModel.getId();
        if(redisTemplate.hasKey(key) == Boolean.FALSE){
            throw new BusinessException(ErrorCodeEnum.TOKEN_ERROR.code, ErrorCodeEnum.TOKEN_ERROR.msg);
        }
        // 对比redis登录缓存的token进行比较
        UserMsgModel redisUserMsg = redisTemplate.opsForValue().get(key);
        if(null == redisUserMsg || !redisUserMsg.getToken().equals(authorization)){
            throw new BusinessException(ErrorCodeEnum.LOGIN_TIME_OUT.code, ErrorCodeEnum.LOGIN_TIME_OUT.msg);
        }

        // 校验权限
        String urlPath = request.getServletPath();
        List<SystemPermissionModel> allPermission = permissionService.getSystemPermissionList();
        // 系统接口权限
        List<SystemPermissionModel> pathPermissionList = allPermission.stream()
                .filter(it -> it.getUrlPath().contains(urlPath) &&
                        it.getControlType().equals(SystemPermissionControlTypeEnum.BUTTON_FILED.getCode()))
                .collect(Collectors.toList());
        // 用户该接口权限
        List<SystemPermissionModel> userPermission = redisUserMsg.getPermissionList()
                .stream()
                .collect(Collectors.groupingBy(SystemPermissionModel::getUrlPath))
                .getOrDefault(urlPath, Collections.emptyList());
        // 当接口存在权限时，校验是否存在权限
        if (!pathPermissionList.isEmpty()) {
            Set<Integer> controlType = userPermission.stream()
                    .map(SystemPermissionModel::getControlType)
                    .collect(Collectors.toSet());
            if (!controlType.contains(SystemPermissionControlTypeEnum.LIST.getCode())
                    && !controlType.contains(SystemPermissionControlTypeEnum.BUTTON_FILED.getCode())) {
                throw new BusinessException(ErrorCodeEnum.PERMISSION_DENIED);
            }
        }
        checkPermission(userPermission, request);
        // redis刷新缓存过期时间
        redisTemplate.expire(key, GlobalConst.REDIS_TIME_OUT, TimeUnit.SECONDS);

        return true;
    }

    /**
     * 校验按钮、查询参数、列表等权限
     *
     * @author dude
     * @date 2021/3/27
     **/
    private void checkPermission(List<SystemPermissionModel> permissionList, HttpServletRequest request) {
        if (permissionList == null || permissionList.isEmpty()) {
            return;
        }
        List<String> searchFiled = permissionList.stream()
                .filter(it -> it.getControlType().equals(SystemPermissionControlTypeEnum.SEARCH_FILED.getCode()))
                .map(SystemPermissionModel::getFiledName)
                .collect(Collectors.toList());
        if (searchFiled.isEmpty()) {
            return;
        }
        String paramStr = Utils.getBody(request);
        if(StringUtils.isBlank(paramStr)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        Map<String, String> paramMap = JacksonUtil.json2Map(paramStr);
        searchFiled.forEach(each -> {
            String data = paramMap.getOrDefault(each, "");
            if (StringUtils.isBlank(data)) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
            }
        });
    }
}
