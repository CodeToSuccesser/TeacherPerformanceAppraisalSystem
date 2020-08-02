package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.tpas.entity.UserLog;
import com.management.tpas.dao.UserLogMapper;
import com.management.tpas.service.UserLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户访问记录表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class UserLogServiceImpl extends BaseServiceImpl<UserLogMapper, UserLog> implements UserLogService {

}
