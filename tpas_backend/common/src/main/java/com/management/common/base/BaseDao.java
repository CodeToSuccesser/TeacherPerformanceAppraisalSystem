package com.management.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author dude
 * @description 基础的dao接口，所有的dao都要继承这个接口  如果有什么通用的方法，可自行扩展
 *
 * @date 2020/7/16
 **/
interface BaseDao<T> extends BaseMapper<T>{}
