package com.management.tpas.service;

import com.management.common.base.BaseService;
import com.management.tpas.entity.TeacherMsg;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.RegisterMsgModel;
import com.management.tpas.model.TeacherMsgModel;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 教师账号信息表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public interface TeacherMsgService extends BaseService<TeacherMsg> {

    /**
     * @description 验证登录信息, 返回账号信息
     * @param loginMsgModel 登录信息
     * @return String
     *
     * @author dude
     * @date 2020/8/10
     **/
    Map getByLoginMsg(LoginMsgModel loginMsgModel);

    /**
     * @param registerMsgModel
     * @Description 插入一条教师信息
     * @Return TeacherMsgModel
     * @Author peihua.wu
     * @Date 2020/8/16
     **/
    TeacherMsgModel insertTeacherMsg(RegisterMsgModel registerMsgModel);

}
