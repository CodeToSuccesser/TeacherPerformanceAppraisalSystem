package com.management.tpas.service;

import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;
import com.management.common.model.UploadResponseModel;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.RegisterMsgModel;
import com.management.tpas.model.UserInfoSearchModel;
import com.management.tpas.model.UserMsgModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     *
     * @author dude
     * @date 2021/2/10
     **/
    void editUserInfo(UserMsgModel model);

    /**
     * @param model 修改信息
     * @param file  头像文件 可为空
     * @return com.management.tpas.model.TeacherMsgModel 修改后信息
     * @description 修改用户信息
     * @author dude
     * @date 2021/2/9
     **/
    UserMsgModel updateUserMsg(RegisterMsgModel model, MultipartFile file);

    /**
     * 获取用户列表
     * @param searchModel 查询参数
     * @return com.github.pagehelper.PageInfo<com.management.tpas.model.UserMsgModel>
     *
     * @author dude
     * @date 2021/3/29
     **/
    PageInfo<UserMsgModel> queryUserInfo(UserInfoSearchModel searchModel);

    /**
     * 删除用户信息
     *
     * @author dude
     * @date 2021/3/29
     **/
    UploadResponseModel<String> deleteUser(List<String> logNameList);

    /**
     * 重设密码为123qwe
     *
     * @author dude
     * @date 2021/3/29
     **/
    UploadResponseModel<String> resetUserPassword(List<String> logNameList);

    /**
     * 插入用户数据
     *
     * @author dude
     * @date 2021/3/29
     **/
    void insertBatchUserMsg(List<UserMsg> userMsgList);

    /**
     * 查询用户信息，用于数据导出
     *
     * @author dude
     * @date 2021/3/30
     **/
    List<UserMsgModel> getUserModelList(UserInfoSearchModel searchModel);
}
