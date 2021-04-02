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
import com.management.common.utils.JacksonUtil;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    private RuleSettingMapper ruleSettingMapper;

    @Autowired
    private ParamsRulesSettingMapper paramsRulesSettingMapper;

    @Autowired
    private CourseScoreMapper courseScoreMapper;

    @Autowired
    private InternScoreMapper internScoreMapper;

    @Autowired
    private PaperScoreMapper paperScoreMapper;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calculateAssessment(AssessCalculateSearchModel model) {
        List<AssessRule> assessRuleList = null;
        List<Map<String, Object>> dataList = null;
        // TODO: 分批查询
        // 获取需要统计的数据
        switch (RuleSettingCTypeEnum.getEnumByCode(model.getcType())) {
            case COURSE: {
                CourseHoursSearchModel courseHoursSearchModel = new CourseHoursSearchModel();
                courseHoursSearchModel.setSchoolYear(model.getSchoolYear());
                courseHoursSearchModel.setSemester(model.getSemester());
                List<CourseHoursModel> courseList = courseHoursMapper.selectCourseHours(courseHoursSearchModel);
                if (!courseList.isEmpty()) {
                    dataList = courseList.stream().map(JacksonUtil::object2Map).collect(Collectors.toList());
                }
                break;
            }
            case PAPER: {
                PaperSearchModel paperSearchModel = new PaperSearchModel();
                paperSearchModel.setSchoolYear(model.getSchoolYear());
                paperSearchModel.setSemester(model.getSemester());
                List<PaperModel> paperList = paperMapper.selectPaperInfo(paperSearchModel);
                if (!paperList.isEmpty()) {
                    dataList = paperList.stream().map(JacksonUtil::object2Map).collect(Collectors.toList());
                }
                break;
            }
            case INTERN: {
                InternSearchModel internSearchModel = new InternSearchModel();
                internSearchModel.setSchoolYear(model.getSchoolYear());
                internSearchModel.setSemester(model.getSemester());
                List<InternModel> internList = internMapper.selectInternInfo(internSearchModel);
                if (!internList.isEmpty()) {
                    dataList = internList.stream().map(JacksonUtil::object2Map).collect(Collectors.toList());
                }
                break;
            }
        }
        if (dataList != null && !dataList.isEmpty()) {
            assessRuleList = assessRuleMapper.selectByCType(model.getcType());
        }
        if (assessRuleList == null || assessRuleList.isEmpty()) {
            return;
        }
        // 筛选需要进行统计的assess
        List<AssessRule> finalAssessRuleList = assessRuleList;
        dataList.forEach(eachData -> {
            List<AssessRule> assessRuleForData = new ArrayList<>();
            Set<Long> ruleId = new HashSet<>();
            finalAssessRuleList.forEach(eachAssess -> {
                if (!StringUtils.isBlank(eachAssess.getRuleSettingIds())) {
                    List<Long> ruleIdList = AssessRuleUtil.parseIdList(eachAssess.getRuleSettingIds());
                    List<RuleSetting> ruleList = ruleSettingMapper.queryByIdList(ruleIdList);
                    List<Long> checkedRuleId = AssessRuleUtil.checkRules(eachData, ruleList);
                    if (!checkedRuleId.isEmpty()) {
                        ruleId.addAll(checkedRuleId);
                        if (checkedRuleId.size() == ruleList.size()) {
                            assessRuleForData.add(eachAssess);
                        }
                    }
                } else {
                    assessRuleForData.add(eachAssess);
                }
            });
            if (!assessRuleForData.isEmpty()) {
                calculateAssessRules(eachData, assessRuleForData, ruleId, model.getcType());
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Assessment> queryAssessList(ScoreSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<Assessment> data = assessmentMapper.queryAssessList(searchModel);
        return new PageInfo<>(data);
    }

    /**
     * 统计一个工作量对应的绩效
     *
     * @author dude
     * @date 2021/3/5
     **/
    @Transactional(rollbackFor = Exception.class)
    void calculateAssessRules(Map<String, Object> data, List<AssessRule> assessRuleList, Set<Long> ruleId, Integer cType) {
        Map<Integer, BigDecimal> paramValueMap = new HashMap<>();// 已获取的cNum
        Assessment assessment = new Assessment(Long.valueOf(data.getOrDefault("teacherId", 0).toString()),
                (Integer)data.getOrDefault("semester", 0),
                (String)data.getOrDefault("schoolYear", ""));
        for (AssessRule assessRule: assessRuleList) {
            if (!StringUtils.isBlank(assessRule.getRuleSettingIds())) {
                List<Long> ruleFromAssess = AssessRuleUtil.parseIdList(assessRule.getRuleSettingIds());
                if (!ruleFromAssess.isEmpty()) {
                    ruleId.addAll(ruleFromAssess);
                }
            }
        }
        for (AssessRule assessRule: assessRuleList) {
            List<Integer> paramCNum = AssessRuleUtil.parseParamsFromAssessDetail(assessRule.getAssessDetail(), false);
            String assessFormat;
            BigDecimal assessScore;
            if (paramCNum.isEmpty()) {
                assessFormat = assessRule.getAssessDetail();
            } else {
                // 获取可选的param，查询未获取的cNum，符合条件的rule加入ruleID
                List<Integer> cNumForParse = paramCNum.stream().filter(it -> !paramValueMap.containsKey(it)).collect(Collectors.toList());
                if (!cNumForParse.isEmpty()) {
                    List<ParamsRulesSetting> paramsRulesList = paramsRulesSettingMapper.queryByCNumList(cNumForParse);
                    List<Long> ruleIdForCheck = new ArrayList<>();
                    for (ParamsRulesSetting paramsRulesSetting : paramsRulesList) {
                        if (!StringUtils.isBlank(paramsRulesSetting.getRulesSettingIds())) {
                            List<Long> ruleFromParam = AssessRuleUtil.parseIdList(paramsRulesSetting.getRulesSettingIds())
                                    .stream().filter(it -> !ruleId.contains(it) && !ruleIdForCheck.contains(it))
                                    .collect(Collectors.toList());
                            if (!ruleFromParam.isEmpty()) {
                                ruleIdForCheck.addAll(ruleFromParam);
                            }
                        }
                    }
                    List<RuleSetting> ruleForCheck = ruleSettingMapper.queryByIdList(ruleIdForCheck);
                    ruleId.addAll(AssessRuleUtil.checkRules(data, ruleForCheck));
                    paramValueMap.putAll(AssessRuleUtil.selectParamValue(data, paramsRulesList, ruleId));
                }
                assessFormat = AssessRuleUtil.parseFormat(assessRule.getAssessDetail(), paramValueMap);
            }
            assessScore = AssessRuleUtil.calculate(assessFormat);
            // 插入新的分数统计
            switch (RuleSettingCTypeEnum.getEnumByCode(assessRule.getcType())) {
                case COURSE:{
                    CourseScore courseScore = new CourseScore();
                    courseScore.setAdminId(UserUtil.getUserId());
                    courseScore.setCourseHoursId(Long.valueOf(data.getOrDefault("id", 0).toString()));
                    courseScore.setTeacherId(assessment.getTeacherId());
                    courseScore.setSchoolYear(assessment.getSchoolYear());
                    courseScore.setSemester(assessment.getSemester());
                    courseScore.setAssessRuleId(assessRule.getId());
                    courseScore.setAssessDetail(assessRule.getAssessDetail());
                    courseScore.setAssessFormat(assessFormat);
                    courseScore.setTotal(assessScore);
                    assessment.setCourseQuality(assessment.getCourseQuality().add(assessScore));
                    courseScoreMapper.updateOrInsert(courseScore);
                    break;
                }
                case INTERN:{
                    InternScore internScore = new InternScore();
                    internScore.setAdminId(UserUtil.getUserId());
                    internScore.setInternId(Long.valueOf(data.getOrDefault("id", 0).toString()));
                    internScore.setTeacherId(assessment.getTeacherId());
                    internScore.setSchoolYear(assessment.getSchoolYear());
                    internScore.setSemester(assessment.getSemester());
                    internScore.setAssessRuleId(assessRule.getId());
                    internScore.setAssessDetail(assessRule.getAssessDetail());
                    internScore.setAssessFormat(assessFormat);
                    internScore.setTotal(assessScore);
                    assessment.setInternQuality(assessment.getInternQuality().add(assessScore));
                    internScoreMapper.updateOrInsert(internScore);
                    break;
                }
                case PAPER:{
                    PaperScore paperScore = new PaperScore();
                    paperScore.setAdminId(UserUtil.getUserId());
                    paperScore.setPaperId(Long.valueOf(data.getOrDefault("id", 0).toString()));
                    paperScore.setSchoolYear((String)data.getOrDefault("schoolYear", ""));
                    paperScore.setTeacherId(assessment.getTeacherId());
                    paperScore.setSchoolYear(assessment.getSchoolYear());
                    paperScore.setSemester(assessment.getSemester());
                    paperScore.setAssessRuleId(assessRule.getId());
                    paperScore.setAssessDetail(assessRule.getAssessDetail());
                    paperScore.setAssessFormat(assessFormat);
                    paperScore.setTotal(assessScore);
                    assessment.setPaperQuality(assessment.getPaperQuality().add(assessScore));
                    paperScoreMapper.updateOrInsert(paperScore);
                    break;
                }
            }
        }
        assessment.setTotalQuality(assessment.getCourseQuality()
                .add(assessment.getPaperQuality())
                .add(assessment.getInternQuality())
                .add(assessment.getOtherQuality()));
        assessmentMapper.updateOrInsert(assessment, cType);
    }


}
