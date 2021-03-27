package com.management.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.tpas.entity.SystemPermission;
import com.management.tpas.model.SystemPermissionModel;
import com.management.tpas.model.SystemPermissionSearchModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemPermissionMapper
 * @description 系统菜单详细权限表
 * @date 2021/3/26
 **/
@Repository
public interface SystemPermissionMapper extends BaseMapper<SystemPermission> {

    List<SystemPermissionModel> getSystemPermission(@Param("data") SystemPermissionSearchModel searchModel);

    List<SystemPermissionModel> getSystemPermissionByRoleName(@Param("data") List<String> roleNames);
}
