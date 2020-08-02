package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.dao.TeacherMsgMapper;
import com.management.tpas.entity.TeacherMsg;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.service.TeacherMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 教师账号信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class TeacherMsgServiceImpl extends BaseServiceImpl<TeacherMsgMapper, TeacherMsg>
    implements TeacherMsgService {

    @Autowired
    private TeacherMsgMapper teacherMsgMapper;

    private BeanMapper beanMapper = new BeanMapper();

    @Transactional
    @Override
    public void insertTeacherMsg(TeacherMsgModel teacherMsgModel) {
        //插入前先检查是否存在相同的登录名
        if (teacherMsgMapper.selectByLogName(teacherMsgModel.getLogName()) != null) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_OBJECT_EXIST);
        }

        teacherMsgModel.setCreateTime(LocalDateTime.now());
        teacherMsgModel.setUpdateTime(LocalDateTime.now());
        teacherMsgMapper.insert(beanMapper.map(teacherMsgModel, TeacherMsg.class));
    }
}
