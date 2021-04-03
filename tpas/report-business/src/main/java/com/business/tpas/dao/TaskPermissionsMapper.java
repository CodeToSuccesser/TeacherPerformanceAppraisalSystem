package com.business.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.tpas.entity.TaskPermissions;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 任务下发权限表 Mapper 接口
 * </p>
 *
 * @since 2021-03-30
 */
@Repository
public interface TaskPermissionsMapper extends BaseMapper<TaskPermissions> {

    /**
     * 查找所有任务下发权限
     * @return
     */
    List<TaskPermissions> selectAllTaskPermissions();

    /**
     * 批量删除任务下发权限
     * @param ids
     */
    void deleteTaskPermissionsByIds(@Param("ids")List<Long> ids);

}
