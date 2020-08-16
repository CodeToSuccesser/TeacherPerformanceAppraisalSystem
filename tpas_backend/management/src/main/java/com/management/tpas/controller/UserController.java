package com.management.tpas.controller;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.tpas.enums.UserTypeEnum;
import com.management.tpas.model.AdminMsgModel;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.RegisterMsgModel;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.service.TeacherMsgService;
import com.management.tpas.service.impl.AdminMsgServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dude
 * @version 1.0
 * @classname UserController
 * @description 用户登录接口
 * @date 2020/8/9
 **/
@RestController
@RequestMapping("/user")
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
    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody LoginMsgModel loginMsgModel) {
        if (null == loginMsgModel || StringUtils.isBlank(loginMsgModel.getLogName()) || StringUtils
            .isBlank(loginMsgModel.getLogPassword()) || null == loginMsgModel.getUserType()) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY.code, ErrorCodeEnum.PARAM_IS_EMPTY.msg);
        }
        // 判断用户类型
        UserTypeEnum userTypeEnum = UserTypeEnum.getByFlag(loginMsgModel.getUserType());
        // 用户类型错误
        if (null == userTypeEnum) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG.code, ErrorCodeEnum.PARAM_IS_WRONG.msg);
        }
        // 获取账号信息,生成jwt和设置缓存
        switch (userTypeEnum) {
            case USER_TYPE_TEACHER: {
                return new BaseResponse<>(teacherMsgService.getByLoginMsg(loginMsgModel));
            }
            case USER_TYPE_ADMIN: {
                return new BaseResponse<>(adminMsgService.getByLoginMsg(loginMsgModel));
            }
            default: {
                return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG.code, ErrorCodeEnum.PARAM_IS_WRONG.msg);
            }
        }
    }

    /**
     * @param registerMsgModel:用户姓名、登录名、密码、用户类型
     * @Description 插入一条用户
     * @Return BaseResponse<?>
     * @Author peihua.wu
     * @Date 2020/8/16
     **/
    @PostMapping("/insertUser")
    public BaseResponse<?> insertUser(@RequestBody RegisterMsgModel registerMsgModel) {
        UserTypeEnum userTypeEnum = validRegisterMsgModelAndGetType(registerMsgModel);

        switch (userTypeEnum) {
            case USER_TYPE_TEACHER: {
                TeacherMsgModel teacherMsgModel = teacherMsgService.insertTeacherMsg(registerMsgModel);
                if (teacherMsgModel == null) {
                    throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
                }
                return new BaseResponse<>(teacherMsgModel);
            }
            case USER_TYPE_ADMIN: {
                AdminMsgModel adminMsgModel = adminMsgService.insertAdmin(registerMsgModel);
                if (adminMsgModel == null) {
                    throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
                }
                return new BaseResponse<>(adminMsgModel);
            }
            default: {
                return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG.code, ErrorCodeEnum.PARAM_IS_WRONG.msg);
            }
        }

    }

    private UserTypeEnum validRegisterMsgModelAndGetType(RegisterMsgModel registerMsgModel) {
        if (registerMsgModel == null || StringUtils.isBlank(registerMsgModel.getLogName()) || StringUtils
            .isBlank(registerMsgModel.getRegisterName()) || StringUtils.isBlank(registerMsgModel.getPassword())
            || registerMsgModel.getType() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, ErrorCodeEnum.PARAM_IS_EMPTY.msg);
        }

        // 判断注册用户类型
        UserTypeEnum userTypeEnum = UserTypeEnum.getByFlag(registerMsgModel.getType());
        // 用户类型错误
        if (null == userTypeEnum) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, ErrorCodeEnum.PARAM_IS_EMPTY.msg);
        }

        return userTypeEnum;
    }
}
