package com.management.tpas.dao;

import com.management.common.base.BaseDao;
import com.management.tpas.entity.TeacherMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 教师账号信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface TeacherMsgMapper extends BaseDao<TeacherMsg> {

    TeacherMsg selectByLogName(@Param("logName") String logName);

}
