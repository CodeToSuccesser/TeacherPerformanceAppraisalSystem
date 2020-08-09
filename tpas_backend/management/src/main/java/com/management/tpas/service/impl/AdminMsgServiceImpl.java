package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.entity.AdminMsg;
import com.management.tpas.dao.AdminMsgMapper;
import com.management.tpas.entity.TeacherMsg;
import com.management.tpas.model.AdminMsgModel;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.service.AdminMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员账号信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class AdminMsgServiceImpl extends BaseServiceImpl<AdminMsgMapper, AdminMsg> implements AdminMsgService {

    @Autowired
    private AdminMsgMapper adminMsgMapper;

    @Override
    public AdminMsgModel getByLoginMsg(LoginMsgModel loginMsgModel) {
        AdminMsg adminMsg = adminMsgMapper.selectByLogName(loginMsgModel.getLogName());
        // 账号不存在 或 密码错误
        if (null == adminMsg || !adminMsg.getLogPassword().equals(loginMsgModel.getLogPassword())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        return BeanMapper.map(adminMsg, AdminMsgModel.class);
    }
}
