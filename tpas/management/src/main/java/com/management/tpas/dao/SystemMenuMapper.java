package com.management.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.management.tpas.entity.SystemMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemMenuMapper
 * @description 系统路由菜单权限 mapper
 * @date 2021/3/10
 **/
@Repository
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    int countMenusByValue(@Param("data") List<String> nameList);

    List<SystemMenu> getMenusByValue(@Param("data") Collection<String> nameList);
}
