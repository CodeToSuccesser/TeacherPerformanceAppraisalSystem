package com.management.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.RegisterMsgModel;
import com.management.tpas.model.UserInfoSearchModel;
import com.management.tpas.model.UserMsgModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname UserMsgMapper
 * @description 用户信息表 mapper类
 * @date 2021/2/10
 **/
@Repository
public interface UserMsgMapper extends BaseMapper<UserMsg> {

    UserMsg selectByLogName(@Param("logName") String logName);

    void updateByLogName(@Param("data") RegisterMsgModel model, @Param("adminId") Long adminId);

    List<UserMsg> getUserMsgList();

    List<UserMsgModel> queryUserInfo(@Param("data")UserInfoSearchModel model);

    Integer deleteUser(@Param("data") List<String> logNameList, @Param("adminId") Long adminId);

    Integer resetUserPassword(@Param("data") List<String> logNameList, @Param("password") String password, @Param("adminId") Long adminId);

    void saveBatch(@Param("data") List<UserMsg> userMsgList, @Param("adminId") Long adminId);
}
