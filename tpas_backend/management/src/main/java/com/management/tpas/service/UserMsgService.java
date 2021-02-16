package com.management.tpas.service;

import com.management.common.base.BaseService;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.RegisterMsgModel;
import com.management.tpas.model.UserMsgModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dude
 * @version 1.0
 * @classname UserMsgService
 * @description 用户信息表 逻辑接口
 * @date 2021/2/10
 **/
public interface UserMsgService extends BaseService<UserMsg> {

    /**
     * @param loginMsgModel 登录信息
     * @return String
     * @description 验证登录信息, 返回账号信息
     * @author dude
     * @date 2020/8/10
     **/
    UserMsgModel getByLoginMsg(LoginMsgModel loginMsgModel);

    /**
     * 根据登录名查找用户信息
     * @param logName
     * @return
     */
    UserMsgModel getByLoginName(String logName);


    /**
     * @description 插入一条教师信息
     * @param registerMsgModel 注册信息
     * @return com.management.tpas.model.UserMsgModel
     *
     * @author dude
     * @date 2021/2/10
     **/
    UserMsgModel insertUserMsg(RegisterMsgModel registerMsgModel);

    /**
     * @param model 修改信息
     * @param file  头像文件 可为空
     * @return com.management.tpas.model.TeacherMsgModel 修改后信息
     * @description 修改用户信息
     * @author dude
     * @date 2021/2/9
     **/
    UserMsgModel updateUserMsg(RegisterMsgModel model, MultipartFile file);
}
