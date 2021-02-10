package com.management.tpas.controller;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.tpas.enums.UserTypeEnum;
import com.management.tpas.model.*;
import com.management.tpas.service.impl.UserMsgServiceImpl;
import com.management.tpas.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dude
 * @version 1.0
 * @classname UserController
 * @description 用户登录接口
 * @date 2020/8/9
 **/
@Api(tags = {"用户登录权限相关接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMsgServiceImpl userMsgService;

    /**
     * @param loginMsgModel 登录信息
     * @return java.lang.String
     * @description 登录处理接口
     * @author dude
     * @date 2020/8/9
     **/
    @PostMapping("/login")
    @ApiOperation("登录处理接口")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = UserMsgModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
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
        return new BaseResponse<>(userMsgService.getByLoginMsg(loginMsgModel));
    }

    /**
     * @param registerMsgModel:用户姓名、登录名、密码、用户类型
     * @Description 插入一条用户
     * @Return BaseResponse<?>
     * @Author peihua.wu
     * @Date 2020/8/16
     **/
    @PostMapping("/insertUser")
    @ApiOperation("插入一条用户")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = UserMsgModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> insertUser(@RequestBody RegisterMsgModel registerMsgModel) {
        UserTypeEnum userTypeEnum = validRegisterMsgModelAndGetType(registerMsgModel);
        // 插入新数据
        UserMsgModel userMsgModel = userMsgService.insertUserMsg(registerMsgModel);
        if(userMsgModel == null) {
            logger.warn("用户登录名未存在，插入后查询失败异常");
            throw new BusinessException(ErrorCodeEnum.SYSTEM_BUSING);
        }
        return new BaseResponse<>(userMsgModel);
    }

    @PostMapping("/modifyUserInfo")
    @ApiOperation(value = "编辑用户信息", notes = "个人中心信息编辑")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = UserMsgModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> modifyUserInfo(MultipartFile file, RegisterMsgModel model) {
        // 无新修改字段
        if (StringUtils.isBlank(model.getContact()) && StringUtils.isBlank(model.getPassword()) && file == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        // 判断注册用户类型
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (null == userMsgModel) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_TIME_OUT);
        } else {
            model.setType(userMsgModel.getUserType());
            model.setLogName(userMsgModel.getLogName());
        }
        UserTypeEnum userTypeEnum = UserTypeEnum.getByFlag(model.getType());
        // 用户类型错误
        if (null == userTypeEnum) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        return new BaseResponse<>(userMsgService.updateUserMsg(model, file));
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
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, ErrorCodeEnum.PARAM_IS_WRONG.msg);
        }

        return userTypeEnum;
    }
}
