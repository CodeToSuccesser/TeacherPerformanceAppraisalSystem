package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.tpas.dao.SystemMenuMapper;
import com.management.tpas.entity.SystemMenu;
import com.management.tpas.service.SystemMenuService;
import org.springframework.stereotype.Service;

/**
 * @author dude
 * @version 1.0
 * @classname SystemMenuImpl
 * @description 菜单权限管理
 * @date 2021/3/10
 **/
@Service
public class SystemMenuImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {
}
