package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.common.config.GlobalConst;
import com.management.common.config.JwtConfig;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.common.utils.JacksonUtil;
import com.management.common.utils.JwtUtil;
import com.management.tpas.dao.TeacherMsgMapper;
import com.management.tpas.entity.TeacherMsg;
import com.management.tpas.enums.UserTypeEnum;
import com.management.tpas.model.LoginMsgModel;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.TeacherMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 教师账号信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class TeacherMsgServiceImpl extends BaseServiceImpl<TeacherMsgMapper, TeacherMsg>
        implements TeacherMsgService {

    @Autowired
    private TeacherMsgMapper teacherMsgMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional
    @Override
    public void insertTeacherMsg(TeacherMsgModel teacherMsgModel) {
        //插入前先检查是否存在相同的登录名
        if (teacherMsgMapper.selectByLogName(teacherMsgModel.getLogName()) != null) {
            throw new BusinessException(ErrorCodeEnum.DUPLICATE_OBJECT_EXIST);
        }

        teacherMsgModel.setCreateTime(new Date());
        teacherMsgModel.setUpdateTime(new Date());
        teacherMsgMapper.insert(BeanMapper.map(teacherMsgModel, TeacherMsg.class));
    }

    /**
     * @param loginMsgModel 登录信息
     * @return com.management.tpas.model.TeacherMsgModel
     * @description 由登录信息查询账号
     * @author dude
     * @date 2020/8/9
     **/
    @Override
    @Transactional
    public Map<String, String> getByLoginMsg(LoginMsgModel loginMsgModel) {
        TeacherMsg teacherMsg = teacherMsgMapper.selectByLogName(loginMsgModel.getLogName());
        // 账号不存在 或 密码错误
        if (null == teacherMsg || !teacherMsg.getLogPassword().equals(loginMsgModel.getLogPassword())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        // 获取账号信息
        UserMsgModel userMsgModel = BeanMapper.map(teacherMsg, UserMsgModel.class);
        userMsgModel.setUserName(teacherMsg.getTeacherName());
        userMsgModel.setUserType(UserTypeEnum.USER_TYPE_TEACHER.flag);
        // 生成jwt和设置缓存
        Map<String, String> jwtMap;
        jwtMap = JacksonUtil.object2Map(userMsgModel);// 用户信息转为map
        String key = GlobalConst.REDIS_KEY_PREFIX + userMsgModel.getUserType().toString() + userMsgModel.getId().toString();
        String token = JwtUtil.createJWT(JacksonUtil.object2Json(jwtMap), JwtConfig.JWT_SECRET);
        jwtMap.put(GlobalConst.TOKEN_NAME, token);
        redisTemplate.opsForValue().set(key, JacksonUtil.object2Json(jwtMap), JwtConfig.EXPIRE_TIME);

        return jwtMap;
    }
}
