package com.management.tpas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.config.FileConfig;
import com.management.common.config.GlobalConst;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.UploadResponseModel;
import com.management.common.utils.BeanMapper;
import com.management.common.utils.CommonUtil;
import com.management.common.utils.FileUtil;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.config.JwtConfig;
import com.management.tpas.dao.*;
import com.management.tpas.entity.*;
import com.management.tpas.model.*;
import com.management.tpas.service.UserMsgService;
import com.management.tpas.utils.JwtUtil;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.management.common.config.GlobalConst.DEFAULT_PASSWORD;
import static com.management.common.config.GlobalConst.PASSWORD_FORMAT;

/**
 * @author dude
 * @version 1.0
 * @classname UserMsgServiceImpl
 * @description 用户信息表 逻辑接口实现类
 * @date 2021/2/10
 **/
@Service
public class UserMsgServiceImpl extends BaseServiceImpl<UserMsgMapper, UserMsg> implements UserMsgService {

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Autowired
    private SystemRoleMapper roleMapper;

    @Autowired
    private SystemMenuMapper menuMapper;

    @Autowired
    private SystemPermissionMapper permissionMapper;

    @Resource
    private RedisTemplate<String, UserMsgModel> redisTemplate;

    @Autowired
    private FileConfig fileConfig;

    /**
     * @param loginMsgModel 登录信息
     * @return com.management.tpas.model.TeacherMsgModel
     * @description 由登录信息查询账号
     * @author dude
     * @date 2020/8/9
     **/
    @Override
    @Transactional
    public UserMsgModel getByLoginMsg(LoginMsgModel loginMsgModel) {
        UserMsg userMsg = userMsgMapper.selectByLogName(loginMsgModel.getLogName());
        // 账号不存在 或 密码错误
        if (null == userMsg || !userMsg.getLogPassword().equals(loginMsgModel.getLogPassword())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        // 获取账号信息
        UserMsgModel userMsgModel = BeanMapper.map(userMsg, UserMsgModel.class);
        // 生成jwt和设置缓存
        String key = GlobalConst.REDIS_KEY_PREFIX + userMsgModel.getUserType().toString() + userMsgModel.getId().toString();
        String token = JwtUtil.createJWT(JacksonUtil.object2Json(userMsgModel), JwtConfig.JWT_SECRET);
        userMsgModel.setToken(token);

        // 查找角色、设置菜单、权限
        List<String> roleNames = CommonUtil.parseStringList(userMsgModel.getRolesName(), ",");
        // 查询权限
        List<SystemPermissionModel> permissionList = permissionMapper.getSystemPermissionByRoleName(roleNames);
        userMsgModel.setPermissionList(permissionList);
        userMsgModel.setRouterMenuModels(getRouterMenu(roleNames, permissionList));

        redisTemplate.opsForValue().set(key, userMsgModel);
        //更新过期时间
        redisTemplate.expire(key, JwtConfig.EXPIRE_TIME, TimeUnit.HOURS);

        return userMsgModel;
    }

    @Transactional(readOnly = true)
    @Override
    public UserMsgModel getByLoginName(String logName) {
        return BeanMapper.map(userMsgMapper.selectByLogName(logName), UserMsgModel.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUserInfo(UserMsgModel model) {
        //插入前先检查是否存在相同的登录名
        UserMsg userMsg = userMsgMapper.selectByLogName(model.getLogName());
        // 校验登录名是否占用
        if ((userMsg != null && model.getId() == null)
                || (userMsg != null && !model.getId().equals(userMsg.getId()))) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_OBJECT_EXIST);
        }
        // 校验role
        List<String> roleNames = CommonUtil.parseStringList(model.getRolesName(), ",");
        if (!roleNames.isEmpty()) {
            int count = roleMapper.countRolesByName(roleNames);
            if(count != roleNames.size()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        UserMsg editMsg = BeanMapper.map(model, UserMsg.class);
        userMsgMapper.saveBatch(Collections.singletonList(editMsg), UserUtil.getUserId());
    }

    @Override
    @Transactional
    public UserMsgModel updateUserMsg(RegisterMsgModel model, MultipartFile file) {
        //插入前先检查是否存在相同的登录名
        if (userMsgMapper.selectByLogName(model.getLogName()) == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        // 上传新头像
        if (file != null) {
            String newFileName = model.getLogName() + "_" + (new Date().getTime());
            model.setPortrait(new FileUtil().UploadPortrait(file,
                    fileConfig.baseFilePath.concat(fileConfig.imageFileMenu),
                    newFileName, fileConfig.imageWeight, fileConfig.imageHeight));
        }
        // 校验密码格式
        if (StringUtils.isNotBlank(model.getPassword())) {
            if (!PASSWORD_FORMAT.matcher(model.getPassword()).matches()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        userMsgMapper.updateByLogName(model, UserUtil.getUserId());
        UserMsgModel userMsgModel = BeanMapper.map(userMsgMapper.selectByLogName(model.getLogName()), UserMsgModel.class);
        return userMsgModel;
    }

    private List<RouterMenus> getRouterMenu(List<String> roleNames, List<SystemPermissionModel> permissionList) {
        if (roleNames == null || roleNames.isEmpty()) {
            return Collections.emptyList();
        }
        // 查询角色信息
        List<SystemRoleModel> roles = roleMapper.getRoleByNameList(roleNames);
        if (roles.isEmpty()) {
            return Collections.emptyList();
        }
        // 解析目录信息和目录对应的role
        Map<String, List<String>> menuRoleMap = new HashMap<>();
        for(SystemRoleModel role: roles) {
            List<String> menus = CommonUtil.parseStringList(role.getMenusValue(), ",");
            if (menus == null || menus.isEmpty()) {
                continue;
            }
            menus.forEach(it -> {
                List<String> itemRoles = menuRoleMap.getOrDefault(it, new ArrayList<>());
                itemRoles.add(role.getName());
                menuRoleMap.put(it, itemRoles);
            });
        }
        // 查询目录
        List<SystemMenu> menuList = menuMapper.getMenusByValue(menuRoleMap.keySet());
        Map<String, List<SystemMenu>> menuMap = menuList.stream().collect(Collectors.groupingBy(SystemMenu::getParentValue));
        Map<String, List<SystemPermissionModel>> menuPermissionMap = permissionList.stream()
                .collect(Collectors.groupingBy(SystemPermissionModel::getValue));
        List<RouterMenus> target = parseChildren("", menuMap, menuRoleMap, menuPermissionMap);
        if (target != null) {
            for (RouterMenus each : target) {
                each.setAlwaysShow(true);
            }
        }
        return target;
    }

    /**
     * 解析目录和权限
     * @param parentValue 父目录名称
     * @param menuMap 相同父目录名称对应的子目录信息
     * @param menuRoleMap 每个目录名称相关的role名称
     * @param menuPermissionMap 每个目录名称相关的权限
     * @return java.util.List<com.management.tpas.model.RouterMenusModel>
     *
     * @author dude
     * @date 2021/3/27
     **/
    private List<RouterMenus> parseChildren(String parentValue,
                                            Map<String, List<SystemMenu>> menuMap,
                                            Map<String, List<String>> menuRoleMap,
                                            Map<String, List<SystemPermissionModel>> menuPermissionMap) {
        if (!menuMap.containsKey(parentValue)) {
            return null;
        }
        List<RouterMenus> childrenMenu = new ArrayList<>();
        List<SystemMenu> children = menuMap.get(parentValue);
        for (SystemMenu child: children) {
            RouterMenus childMenu = new RouterMenus();
            childMenu.setName(child.getLabel());
            childMenu.setPath(child.getPath());
            childMenu.setLevel(child.getLevel());
            childMenu.setParentValue(parentValue);
            childMenu.setMeta(new Meta());
            childMenu.setPermissionList(menuPermissionMap.getOrDefault(child.getValue(), null));
            childMenu.getMeta().setIcon(child.getIconType());
            childMenu.getMeta().setTitle(child.getLabel());
            childMenu.getMeta().setRoles(menuRoleMap.getOrDefault(child.getValue(), null));
            childMenu.setChildren(parseChildren(child.getValue(), menuMap, menuRoleMap, menuPermissionMap));
            childrenMenu.add(childMenu);
        }
        return childrenMenu;
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<UserMsgModel> queryUserInfo(UserInfoSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<UserMsgModel> data = userMsgMapper.queryUserInfo(searchModel);
        return new PageInfo<>(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResponseModel<String> deleteUser(List<String> logNameList) {
        Integer effectCount = userMsgMapper.deleteUser(logNameList, UserUtil.getUserId());
        return new UploadResponseModel<>(null,
                effectCount,
                logNameList.size() - effectCount,
                logNameList.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResponseModel<String> resetUserPassword(List<String> logNameList) {
        Integer effectCount = userMsgMapper.resetUserPassword(logNameList, DEFAULT_PASSWORD, UserUtil.getUserId());
        return new UploadResponseModel<>(null,
                effectCount,
                logNameList.size() - effectCount,
                logNameList.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatchUserMsg(List<UserMsg> userMsgList) {
        if (userMsgList == null || userMsgList.isEmpty()){
            return;
        }
        userMsgMapper.saveBatch(userMsgList, UserUtil.getUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserMsgModel> getUserModelList(UserInfoSearchModel searchModel) {
        return userMsgMapper.queryUserInfo(searchModel);
    }
}
