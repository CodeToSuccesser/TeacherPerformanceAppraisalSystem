package com.management.tpas.service;

import com.management.common.base.BaseService;
import com.management.tpas.entity.TeacherMsg;
import com.management.tpas.model.TeacherMsgModel;
import org.springframework.stereotype.Service;

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

    void insertTeacherMsg(TeacherMsgModel teacherMsgModel);

}
