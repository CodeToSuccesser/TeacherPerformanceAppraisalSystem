package com.management.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author dude
 * @description 基础的dao接口，所有的dao都要继承这个接口  如果有什么通用的方法，可自行扩展
 * @date 2020/7/16
 **/
interface BaseDao<T> extends BaseMapper<T> {
    /**
     * 保存实体
     * @param entity
     */
    void save(T entity);

    /**
     * 删除实体
     * @param id
     */
    void delete(Long id);

    /**
     * 更新实体
     * @param entity
     */
    void update(T entity);

    /**
     * 按id查询
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 按id查询
     * @param ids
     * @return
     */
    List<T> getByIds(Long[] ids);

    /**
     * 查询所有
     * @return
     */
    List<T> findAll();
}
