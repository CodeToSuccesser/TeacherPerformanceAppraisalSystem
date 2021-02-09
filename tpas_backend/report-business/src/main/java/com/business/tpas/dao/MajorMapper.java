package com.business.tpas.dao;

import com.business.tpas.entity.Major;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 专业信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface MajorMapper extends BaseDao<Major> {

    Major selectByMajorCode(@Param("majorCode") String majorCode);

}
