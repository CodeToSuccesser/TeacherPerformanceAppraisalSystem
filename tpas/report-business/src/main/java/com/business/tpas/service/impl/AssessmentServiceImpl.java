package com.business.tpas.service.impl;

import com.business.tpas.dao.*;
import com.business.tpas.entity.*;
import com.business.tpas.enums.RuleSettingCTypeEnum;
import com.business.tpas.model.*;
import com.business.tpas.service.AssessmentService;
import com.business.tpas.utils.AssessRuleUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.model.UploadResponseModel;
import com.management.tpas.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.management.common.config.GlobalConst.*;

/**
 * <p>
 * 教师绩效核算记录表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class AssessmentServiceImpl extends BaseServiceImpl<AssessmentMapper, Assessment> implements AssessmentService {

    @Autowired
    private AssessRuleMapper assessRuleMapper;

    @Autowired
    private CourseHoursMapper courseHoursMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private InternMapper internMapper;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResponseModel<?> calculateAssessment(AssessCalculateSearchModel model) {
        int pageNum = 0;
        int pageSize = 100;
        AtomicInteger addNum = new AtomicInteger();
        AtomicInteger failNum = new AtomicInteger();
        // 获取需要统计的数据
        switch (RuleSettingCTypeEnum.getEnumByCode(model.getcType())) {
            case COURSE: {
                List<CourseHoursModel> rejectData = new ArrayList<>();
                CourseHoursSearchModel courseHoursSearchModel = new CourseHoursSearchModel();
                courseHoursSearchModel.setSchoolYear(model.getSchoolYear());
                courseHoursSearchModel.setSemester(model.getSemester());
                PageHelper.startPage(pageNum, pageSize);
                List<CourseHoursModel> courseList;
                do {
                    courseList = courseHoursMapper.selectCourseHoursForAssess(courseHoursSearchModel);
                    courseList.forEach(it -> {
                        String key = COURSE_ASSESS_KEY + it.getId().toString();
                        if (redisTemplate.hasKey(key) == Boolean.FALSE) {
                            addNum.getAndIncrement();
                            it.setAdminId(UserUtil.getUserId());
                            redisTemplate.opsForValue().set(key, it);
                        } else {
                            failNum.getAndIncrement();
                            rejectData.add(it);
                        }
                    });
                    PageHelper.offsetPage(pageNum * pageSize + courseList.size(), pageSize);
                    pageNum++;
                } while (courseList.size()>0);
                return new UploadResponseModel<>(rejectData,
                        addNum.get(), failNum.get(), failNum.addAndGet(addNum.get()));
            }
            case PAPER: {
                List<PaperModel> rejectData = new ArrayList<>();
                PaperSearchModel paperSearchModel = new PaperSearchModel();
                paperSearchModel.setSchoolYear(model.getSchoolYear());
                paperSearchModel.setSemester(model.getSemester());
                PageHelper.startPage(pageNum, pageSize);
                List<PaperModel> paperList;
                do {
                    paperList = paperMapper.selectPaperInfoForAssess(paperSearchModel);
                    paperList.forEach(it -> {
                        String key = PAPER_ASSESS_KEY + it.getId().toString();
                        if (redisTemplate.hasKey(key) == Boolean.FALSE) {
                            addNum.getAndIncrement();
                            it.setAdminId(UserUtil.getUserId());
                            redisTemplate.opsForValue().set(key, it);
                        } else {
                            failNum.getAndIncrement();
                            rejectData.add(it);
                        }
                    });
                    PageHelper.offsetPage(pageNum * pageSize + paperList.size(), pageSize);
                    pageNum++;
                } while (paperList.size()>0);
                return new UploadResponseModel<>(rejectData,
                        addNum.get(), failNum.get(), failNum.addAndGet(addNum.get()));
            }
            case INTERN: {
                List<InternModel> rejectData = new ArrayList<>();
                InternSearchModel internSearchModel = new InternSearchModel();
                internSearchModel.setSchoolYear(model.getSchoolYear());
                internSearchModel.setSemester(model.getSemester());
                PageHelper.startPage(pageNum, pageSize);
                List<InternModel> internList;
                do {
                    internList = internMapper.selectInternInfoForAssess(internSearchModel);
                    internList.forEach(it -> {
                        String key = INTERN_ASSESS_KEY + it.getId().toString();
                        if (redisTemplate.hasKey(key) == Boolean.FALSE) {
                            addNum.getAndIncrement();
                            it.setAdminId(UserUtil.getUserId());
                            redisTemplate.opsForValue().set(key, it);
                        } else {
                            failNum.getAndIncrement();
                            rejectData.add(it);
                        }
                    });
                    PageHelper.offsetPage(pageNum * pageSize + internList.size(), pageSize);
                    pageNum++;
                } while (internList.size()>0);
                return new UploadResponseModel<>(rejectData,
                        addNum.get(), failNum.get(), failNum.addAndGet(addNum.get()));
            }
        }
        return new UploadResponseModel<>(null, 0, 0, 0);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<AssessmentModel> queryAssessList(ScoreSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<AssessmentModel> data = assessmentMapper.queryAssessList(searchModel);
        return new PageInfo<>(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrInsert(Assessment assessment, Integer cType) {
        assessmentMapper.updateOrInsert(assessment, cType);
    }
}
