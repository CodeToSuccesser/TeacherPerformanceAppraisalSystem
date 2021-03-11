package com.management.tpas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.PageModel;
import com.management.common.utils.CommonUtil;
import com.management.tpas.dao.SystemMenuMapper;
import com.management.tpas.dao.SystemRoleMapper;
import com.management.tpas.dao.UserMsgMapper;
import com.management.tpas.entity.SystemRole;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.SystemRoleModel;
import com.management.tpas.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRoleImpl
 * @description 角色管理接口实现
 * @date 2021/3/10
 **/
@Service
public class SystemRoleImpl extends BaseServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<SystemRoleModel> queryRoles(PageModel pageModel) {
        PageHelper.startPage(pageModel.pageNum, pageModel.pageSize);
        List<SystemRoleModel> list = systemRoleMapper.getRoles();
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemRoleModel> getRoles() {
        return systemRoleMapper.getRoles();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRole(SystemRoleModel roleModel) {
        SystemRole roleById = null;
        if (roleModel.getId() != null) {
            // 检验编辑数据是否存在
            roleById = systemRoleMapper.selectById(roleModel.getId());
            if (roleById == null || roleById.getIsDeleted() != 0) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        // 新增或者编辑角色名校验是否重复
        if (roleById == null || !roleById.getName().equals(roleModel.getName())) {
            if (systemRoleMapper.getRoleByName(roleModel.getName()) != null) {
                throw new BusinessException(ErrorCodeEnum.OBJECT_EXISTED);
            }
        }
        // 检验菜单是否存在
        if (!StringUtils.isBlank(roleModel.getMenusValue())) {
            List<String> menusValueList = CommonUtil.parseStringList(roleModel.getMenusValue(), ",");
            if (!menusValueList.isEmpty() && systemMenuMapper.countMenusByValue(menusValueList) != menusValueList.size()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        if (roleModel.getId() == null){
            systemRoleMapper.insertModel(roleModel);
        } else {
            systemRoleMapper.updateModel(roleModel);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(SystemRoleModel roleModel) {
        List<UserMsg> userMsgs;
        int pageNum = 0;
        int pageSize = 10;
        PageHelper.startPage(pageNum, 10);
        // 校验所删除的规则是否被取用
        do {
            userMsgs = userMsgMapper.getUserMsgList();
            userMsgs.forEach(it -> {
                List<String> ruleList = CommonUtil.parseStringList(it.getRolesName(), ",");
                if (ruleList.contains(roleModel.getName())) {
                    throw new BusinessException(ErrorCodeEnum.DATA_IS_USING);
                }
            });
            PageHelper.offsetPage(pageNum * pageSize + userMsgs.size(), pageSize);
            pageNum++;
        } while (userMsgs.size()>0);
        systemRoleMapper.deletedModel(roleModel);
    }


}
