package com.management.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.tpas.entity.SystemRole;
import com.management.tpas.model.SystemRoleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRuleMapper
 * @description 系统角色表 mapper
 * @date 2021/3/10
 **/
@Repository
public interface SystemRoleMapper extends BaseMapper<SystemRole> {

    List<SystemRoleModel> getRoles();

    SystemRoleModel getRoleByName(@Param("name") String name);

    void insertModel(@Param("data") SystemRoleModel model);

    void updateModel(@Param("data") SystemRoleModel model);

    int countMenusByName(@Param("data") List<String> nameList);

    List<SystemRoleModel> getRoleByNameList(@Param("data") List<String> nameList);

    void deletedModel(@Param("data") SystemRoleModel model);
}
