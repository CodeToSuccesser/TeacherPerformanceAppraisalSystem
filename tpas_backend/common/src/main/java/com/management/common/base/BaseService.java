package com.management.common.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dude
 * @description 基础的service接口，所有的service都要继承这个接口  如果有什么通用的方法，可自行扩展
 * @date 2020/7/16
 **/
public interface BaseService<T> extends IService<T> {
}
