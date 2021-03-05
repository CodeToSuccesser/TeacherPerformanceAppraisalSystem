package com.business.tpas.service.impl;

import com.business.tpas.dao.MajorMapper;
import com.business.tpas.entity.Major;
import com.business.tpas.model.MajorModel;
import com.business.tpas.service.MajorService;
import com.management.common.base.BaseServiceImpl;
import com.management.common.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 专业信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class MajorServiceImpl extends BaseServiceImpl<MajorMapper, Major> implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Transactional(readOnly = true)
    @Override
    public MajorModel selectByMajorCode(String majorCode) {
        return BeanMapper.map(majorMapper.selectByMajorCode(majorCode), MajorModel.class);
    }
}
