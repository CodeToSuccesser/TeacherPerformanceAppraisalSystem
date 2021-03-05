package com.management.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.RegisterMsgModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

    void updateByLogName(@Param("data") RegisterMsgModel model);
}
