package com.business.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.tpas.entity.Task;
import com.business.tpas.model.TaskModel;
import com.business.tpas.model.TaskSearchModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 任务下发内容表 Mapper 接口
 * </p>
 *
 * @since 2021-03-30
 */
@Repository
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 根据条件查找任务下发内容
     * @param searchModel 搜索条件
     * @return
     */
    List<TaskModel> selectTask(@Param("data") TaskSearchModel searchModel);


    /**
     * 根据任务id批量删除
     * @param ids
     */
    void deleteTaskByIds(@Param("ids")List<Long> ids);


}
