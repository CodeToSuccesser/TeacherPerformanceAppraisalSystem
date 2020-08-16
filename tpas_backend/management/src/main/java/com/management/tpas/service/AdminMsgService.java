package com.management.tpas.service;

import com.management.common.base.BaseService;
import com.management.tpas.entity.AdminMsg;
import com.management.tpas.model.AdminMsgModel;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.RegisterMsgModel;

import java.util.Map;

/**
 * <p>
 * 管理员账号信息表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface AdminMsgService extends BaseService<AdminMsg> {

    /**
     * @description 由登录信息获取账号
     * @param loginMsgModel 登录信息
     * @return String
     *
     * @author dude
     * @date 2020/8/9
     **/
    Map<String, String> getByLoginMsg(LoginMsgModel loginMsgModel);

    /**
     * @param registerMsgModel
     * @Description 插入一条管理员信息
     * @Return AdminMsgModel
     * @Author peihua.wu
     * @Date 2020/8/16
     **/
    AdminMsgModel insertAdmin(RegisterMsgModel registerMsgModel);

}
