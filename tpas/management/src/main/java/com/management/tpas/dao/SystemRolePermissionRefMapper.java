package com.management.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.tpas.entity.SystemRolePermissionRef;
import com.management.tpas.model.SystemPermissionModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRolePermissionRefMapper
 * @description 角色权限关系mapper
 * @date 2021/3/26
 **/
@Repository
public interface SystemRolePermissionRefMapper extends BaseMapper<SystemRolePermissionRef> {

    /**
     * 插入或更新数据
     *
     * @author dude
     * @date 2021/3/26
     **/
    void batchInsertOrUpdate(@Param("data") List<SystemPermissionModel> list, @Param("roleName") String roleName);

    List<SystemRolePermissionRef> getRefByRoleName(@Param("data") List<String> roleName);

    void deleteByKeysAndRoleName(@Param("data") List<String> keyList, @Param("roleName") String roleName);
}
