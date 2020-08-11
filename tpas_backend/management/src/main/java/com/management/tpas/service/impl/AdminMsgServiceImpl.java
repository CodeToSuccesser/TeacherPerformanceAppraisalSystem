package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.common.config.GlobalConst;
import com.management.common.config.JwtConfig;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.common.utils.BeanMapper;
import com.management.common.utils.JacksonUtil;
import com.management.common.utils.JwtUtil;
import com.management.tpas.entity.AdminMsg;
import com.management.tpas.dao.AdminMsgMapper;
import com.management.tpas.entity.TeacherMsg;
import com.management.tpas.enums.UserTypeEnum;
import com.management.tpas.model.AdminMsgModel;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.AdminMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 管理员账号信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class AdminMsgServiceImpl extends BaseServiceImpl<AdminMsgMapper, AdminMsg> implements AdminMsgService {

    @Autowired
    private AdminMsgMapper adminMsgMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String getByLoginMsg(LoginMsgModel loginMsgModel) {
        AdminMsg adminMsg = adminMsgMapper.selectByLogName(loginMsgModel.getLogName());
        // 账号不存在 或 密码错误
        if (null == adminMsg || !adminMsg.getLogPassword().equals(loginMsgModel.getLogPassword())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }

        // 获取账号信息
        UserMsgModel userMsgModel = BeanMapper.map(adminMsg, UserMsgModel.class);
        userMsgModel.setUserName(adminMsg.getAdminName());
        userMsgModel.setUserType(UserTypeEnum.USER_TYPE_ADMIN.flag);
        // 生成jwt和设置缓存
        Map<String, String> jwtMap;
        jwtMap = JacksonUtil.object2Map(userMsgModel);// 用户信息转为map
        String key = GlobalConst.REDIS_KEY_PREFIX + userMsgModel.getUserType().toString() + userMsgModel.getId().toString();
        String token = JwtUtil.createJWT(JacksonUtil.object2Json(jwtMap), JwtConfig.JWT_SECRET);
        jwtMap.put(GlobalConst.TOKEN_NAME, token);
        redisTemplate.opsForValue().set(key, JacksonUtil.object2Json(jwtMap), JwtConfig.EXPIRE_TIME);

        return JacksonUtil.object2Json(new BaseResponse<>(jwtMap));
    }
}
