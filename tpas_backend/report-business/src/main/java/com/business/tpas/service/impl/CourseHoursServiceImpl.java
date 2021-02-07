package com.business.tpas.service.impl;

import com.business.tpas.dao.CourseHoursMapper;
import com.business.tpas.entity.CourseHours;
import com.business.tpas.service.CourseHoursService;
import com.management.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程课时具体信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class CourseHoursServiceImpl extends BaseServiceImpl<CourseHoursMapper, CourseHours> implements CourseHoursService {

    @Autowired
    private CourseHoursMapper courseHoursMapper;

    @Override
    public List<CourseHours> selectByCourseId(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("courseId", id);

        return courseHoursMapper.selectByMap(map);
    }
}
