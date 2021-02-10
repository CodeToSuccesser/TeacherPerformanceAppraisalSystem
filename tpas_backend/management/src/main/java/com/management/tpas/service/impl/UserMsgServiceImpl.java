package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.common.config.FileConfig;
import com.management.common.config.GlobalConst;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.common.utils.FileUtil;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.config.JwtConfig;
import com.management.tpas.dao.UserMsgMapper;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.RegisterMsgModel;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.UserMsgService;
import com.management.tpas.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        // TODO：设置用户类型
        userMsgModel.setUserType(Integer.parseInt(userMsg.getRolesName().split(",")[0]));
        // 生成jwt和设置缓存
        String key = GlobalConst.REDIS_KEY_PREFIX + userMsgModel.getUserType().toString() + userMsgModel.getId().toString();
        String token = JwtUtil.createJWT(JacksonUtil.object2Json(userMsgModel), JwtConfig.JWT_SECRET);
        userMsgModel.setToken(token);
        redisTemplate.opsForValue().set(key, userMsgModel);
        //更新过期时间
        redisTemplate.expire(key, JwtConfig.EXPIRE_TIME, TimeUnit.HOURS);

        return userMsgModel;
    }

    @Override
    @Transactional
    public UserMsgModel insertUserMsg(RegisterMsgModel registerMsgModel) {
        //插入前先检查是否存在相同的登录名
        if (userMsgMapper.selectByLogName(registerMsgModel.getLogName()) != null) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_OBJECT_EXIST);
        }
        UserMsg userMsg = BeanMapper.map(registerMsgModel, UserMsg.class);
        userMsg.setUserName(registerMsgModel.getRegisterName());
        userMsg.setRolesName(registerMsgModel.getType().toString());
        userMsg.setLogPassword(registerMsgModel.getPassword());
        userMsgMapper.insert(userMsg);
        UserMsgModel userMsgModel = BeanMapper.map(userMsgMapper.selectByLogName(registerMsgModel.getLogName()), UserMsgModel.class);
        // TODO: ：设置用户类型
        userMsgModel.setUserType(Integer.parseInt(userMsgModel.getRolesName().split(",")[0]));
        return userMsgModel;
    }

    @Override
    @Transactional
    public UserMsgModel updateUserMsg(RegisterMsgModel model, MultipartFile file) {
        // 上传新头像
        if (file != null) {
            String newFileName = model.getLogName() + "_" + (new Date().getTime());
            model.setPortrait(new FileUtil().UploadPortrait(file, fileConfig.baseFilePath, newFileName, fileConfig.imageWeight, fileConfig.imageHeight));
        }
        // 校验密码格式
        if (StringUtils.isNotBlank(model.getPassword())) {
            if (!PASSWORD_FORMAT.matcher(model.getPassword()).matches()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        userMsgMapper.updateByLogName(model);
        UserMsgModel userMsgModel = BeanMapper.map(userMsgMapper.selectByLogName(model.getLogName()), UserMsgModel.class);
        // TODO: ：设置用户类型
        userMsgModel.setUserType(Integer.parseInt(userMsgModel.getRolesName().split(",")[0]));
        return userMsgModel;
    }
}
