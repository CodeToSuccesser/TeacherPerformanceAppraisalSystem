package com.business.tpas.service.impl;

import com.business.tpas.dao.MajorMapper;
import com.business.tpas.dao.PaperMapper;
import com.business.tpas.entity.Major;
import com.business.tpas.model.MajorModel;
import com.business.tpas.model.MajorSearchModel;
import com.business.tpas.service.MajorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PaperMapper paperMapper;

    @Transactional(readOnly = true)
    @Override
    public MajorModel selectByMajorCode(String majorCode) {
        return BeanMapper.map(majorMapper.selectByMajorCode(majorCode), MajorModel.class);
    }

    @Transactional
    @Override
    public void insertBatchMajor(List<Major> majors) {
        if(CollectionUtils.isEmpty(majors)){
            return;
        }
        majorMapper.saveBatch(majors);
    }

    @Override
    public PageInfo<MajorModel> getMajorByPage(MajorSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<MajorModel> majorModels = majorMapper.selectMajor(searchModel);
        return new PageInfo<>(majorModels);
    }

    @Override
    public List<MajorModel> getMajor(MajorSearchModel searchModel) {
        return majorMapper.selectMajor(searchModel);
    }

    @Transactional
    @Override
    public void modifyMajor(MajorModel majorModel) {
        Major oldMajor = majorMapper.selectById(majorModel.getId());
        if (oldMajor == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "专业信息记录找不到，修改失败");
        }
        if (majorMapper.selectByMajorCode(majorModel.getMajorCode()) != null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_EXISTED.code, "修改后的专业编码冲突，修改失败");
        }
        oldMajor.setMajorCode(majorModel.getMajorCode());
        oldMajor.setMajorName(majorModel.getMajorName());
        majorMapper.updateById(oldMajor);
    }

    @Transactional
    @Override
    public void deleteMajor(List<Long> ids) {
        validateMajorCanBeDeleted(ids);
        // 逻辑删除课时记录
        majorMapper.deleteMajorByIds(ids);
    }

    @Transactional
    @Override
    public void insertMajor(MajorModel majorModel) {
        Major major = BeanMapper.map(majorModel, Major.class);
        major.setAdminId(UserUtil.getUserId());
        majorMapper.insert(major);
    }

    /**
     * 校验专业能否被删除
     * 是否与论文信息依赖
     * @param ids
     */
    private void validateMajorCanBeDeleted(List<Long> ids) {
        Iterator<Long> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next();

            Map<String, Object> map = new HashMap<>();
            map.put("major_id", id);
            map.put("is_deleted", 0);
            if (CollectionUtils.isNotEmpty(paperMapper.selectByMap(map))) {
                iterator.remove();
            }
        }
    }
}
