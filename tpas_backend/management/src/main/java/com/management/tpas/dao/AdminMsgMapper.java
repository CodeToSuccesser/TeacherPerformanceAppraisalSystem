package com.management.tpas.dao;

import com.management.common.base.BaseDao;
import com.management.tpas.entity.AdminMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 管理员账号信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface AdminMsgMapper extends BaseDao<AdminMsg> {

    /**
     * @param logName 登录名
     * @return com.management.tpas.entity.AdminMsg
     * @description 由登录名查询账号
     * @author dude
     * @date 2020/8/9
     **/
    AdminMsg selectByLogName(@Param("logName") String logName);

}
