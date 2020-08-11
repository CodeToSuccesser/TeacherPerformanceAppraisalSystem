package com.management.tpas.controller;

import com.management.common.config.JwtConfig;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.model.BaseResponse;
import com.management.common.utils.JacksonUtil;
import com.management.common.utils.JwtUtil;
import com.management.common.utils.RedisUtil;
import com.management.tpas.enums.UserTypeEnum;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.TeacherMsgService;
import com.management.tpas.service.impl.AdminMsgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import static com.management.common.config.GlobalConst.*;

/**
 * @author dude
 * @version 1.0
 * @classname UserController
 * @description 用户登录接口
 * @date 2020/8/9
 **/
@RestController
public class UserController {

    @Autowired
    private TeacherMsgService teacherMsgService;

    @Autowired
    private AdminMsgServiceImpl adminMsgService;

    /**
     * @param loginMsgModel 登录信息
     * @return java.lang.String
     * @description 登录处理接口
     * @author dude
     * @date 2020/8/9
     **/
    @PostMapping("login")
    public String login(@RequestBody LoginMsgModel loginMsgModel) {
        if (null == loginMsgModel || null == loginMsgModel.getLogName() || null == loginMsgModel.getLogPassword() || null == loginMsgModel.getUserType()) {
            return JacksonUtil.object2Json(new BaseResponse<Object>(ErrorCodeEnum.PARAM_IS_EMPTY.code, ErrorCodeEnum.PARAM_IS_EMPTY.msg));
        }
        // 判断用户类型
        UserTypeEnum userTypeEnum = UserTypeEnum.getByFlag(loginMsgModel.getUserType());
        // 用户类型错误
        if (null == userTypeEnum) {
            return JacksonUtil.object2Json(new BaseResponse<Object>(ErrorCodeEnum.PARAM_IS_WRONG.code, ErrorCodeEnum.PARAM_IS_WRONG.msg));
        }
        // 获取账号信息,生成jwt和设置缓存
        switch (userTypeEnum) {
            case USER_TYPE_TEACHER: {
                return teacherMsgService.getByLoginMsg(loginMsgModel);
            }
            case USER_TYPE_ADMIN: {
                return adminMsgService.getByLoginMsg(loginMsgModel);
            }
            default: {
                return JacksonUtil.object2Json(new BaseResponse<Object>(ErrorCodeEnum.PARAM_IS_WRONG.code, ErrorCodeEnum.PARAM_IS_WRONG.msg));
            }
        }
    }
}
