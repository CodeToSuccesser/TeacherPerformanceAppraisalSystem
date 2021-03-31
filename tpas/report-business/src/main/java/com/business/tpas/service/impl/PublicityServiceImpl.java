package com.business.tpas.service.impl;

import com.business.tpas.dao.PublicityMapper;
import com.business.tpas.entity.Publicity;
import com.business.tpas.model.PublicityModel;
import com.business.tpas.model.PublicitySearchModel;
import com.business.tpas.service.PublicityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 公示信息表 服务实现类
 * </p>
 *
 * @author xxxx
 * @since 2021-03-30
 */
@Service
public class PublicityServiceImpl extends BaseServiceImpl<PublicityMapper, Publicity> implements PublicityService {

    @Autowired
    private PublicityMapper publicityMapper;

    @Override
    public PageInfo<PublicityModel> getPublicityByPage(PublicitySearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<PublicityModel> publicityModels = publicityMapper.selectPublicity(searchModel);
        return new PageInfo<>(publicityModels);
    }

    @Override
    @Transactional
    public void modifyPublicity(PublicityModel publicityModel) {
        Publicity publicity = publicityMapper.selectById(publicityModel.getId());
        publicity.setTitle(publicityModel.getTitle());
        publicity.setContent(publicityModel.getContent());

        publicityMapper.updateById(publicity);
    }

    @Override
    @Transactional
    public void deletePublicity(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "删除id列表为空，删除失败");
        }
        publicityMapper.deletePublicityByIds(ids);
    }

    @Override
    @Transactional
    public void insertPublicity(PublicityModel publicityModel) {
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (userMsgModel == null || userMsgModel.getId() == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "请求身份缺失，插入失败");
        }
        Publicity publicity = new Publicity();
        publicity.setPublisherId(userMsgModel.getId());
        publicity.setTitle(publicityModel.getTitle());
        publicity.setContent(publicityModel.getContent());

        publicityMapper.insert(publicity);
    }
}
