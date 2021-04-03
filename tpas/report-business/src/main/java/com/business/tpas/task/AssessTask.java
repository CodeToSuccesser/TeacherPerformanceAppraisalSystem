package com.business.tpas.task;

import com.business.tpas.entity.*;
import com.business.tpas.enums.RuleSettingCTypeEnum;
import com.business.tpas.model.CourseHoursModel;
import com.business.tpas.model.InternModel;
import com.business.tpas.model.PaperModel;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.service.*;
import com.business.tpas.utils.AssessRuleUtil;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.management.common.config.GlobalConst.*;

/**
 * @author dude
 * @version 1.0
 * @classname AssessTask
 * @description 绩效考核任务
 * @date 2021/4/3
 **/
@Component
@Configuration
public class AssessTask {

    private final Logger logger = LoggerFactory.getLogger(AssessTask.class);

    @Value("${assess-task.course-cron}")
    private String courseCorn;

    @Value("${assess-task.paper-cron}")
    private String paperCorn;

    @Value("${assess-task.intern-cron}")
    private String internCorn;

    @Value("${assess-task.task-switch}")
    private Boolean taskSwitch;

    @Autowired
    private ThreadPoolExecutor commonThreadPool;

    @Autowired
    private CourseScoreService courseScoreService;

    @Autowired
    private PaperScoreService paperScoreService;

    @Autowired
    private InternScoreService internScoreService;

    @Autowired
    private AssessRuleService assessRuleService;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private RuleSettingService ruleSettingService;

    @Autowired
    private ParamsRulesSettingService paramsRulesSettingService;


    @Resource
    private RedisTemplate<String, CourseHoursModel> courseRedisTemplate;

    @Resource
    private RedisTemplate<String, PaperModel> paperRedisTemplate;

    @Resource
    private RedisTemplate<String, InternModel> internRedisTemplate;

    @Bean
    public ScheduledTaskRegistrar initAssessTask() {
        ScheduledTaskRegistrar scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        if (taskSwitch) {
            if (!StringUtils.isBlank(courseCorn)) {
                scheduledTaskRegistrar.addCronTask(this::courseAssessTask, courseCorn);
                logger.info("启动授课考核统计任务 | cron: {}", courseCorn);
            }
            if (!StringUtils.isBlank(paperCorn)) {
                scheduledTaskRegistrar.addCronTask(this::paperAssessTask, paperCorn);
                logger.info("启动论文指导考核统计任务 | cron: {}", paperCorn);
            }
            if (!StringUtils.isBlank(internCorn)) {
                scheduledTaskRegistrar.addCronTask(this::internAssessTask, internCorn);
                logger.info("启动实习指导考核统计任务 | cron: {}", internCorn);
            }
        }
        return scheduledTaskRegistrar;
    }

    /**
     * 读取redis数据，加入线程池进行统计
     **/
    public void courseAssessTask() {
        String regex = COURSE_ASSESS_KEY + "*";
        Set<String> keySet = courseRedisTemplate.keys(regex);
        if (keySet != null && !keySet.isEmpty()) {
            ParamSearchModel searchModel = new ParamSearchModel();
            searchModel.setcType(RuleSettingCTypeEnum.COURSE.getCode());
            List<AssessRule> assessRuleList = assessRuleService.getAssessList(searchModel);
            List<CourseHoursModel> list = courseRedisTemplate.opsForValue().multiGet(keySet);
            if (list != null && !list.isEmpty()) {
                AtomicInteger count = new AtomicInteger();
                list.forEach(each -> {
                    if (courseScoreService.hasScoreChecked(each.getId())) {
                        courseRedisTemplate.delete(COURSE_ASSESS_KEY + each.getId().toString());
                        logger.info("课程绩效|[paperId: {}]已存在统计数据，删除缓存", each.getId());
                    } else {
                        count.getAndIncrement();
                        commonThreadPool.execute(new CalculateTaskThread(RuleSettingCTypeEnum.COURSE,
                                JacksonUtil.object2Map(each),
                                assessRuleList,
                                paramsRulesSettingService,
                                ruleSettingService,
                                assessmentService,
                                assessRuleService,
                                courseScoreService,
                                paperScoreService,
                                internScoreService
                                ));
                    }
                });
            } else {
                logger.info("课程绩效|待统计数量为0，统计结束");
            }
        } else {
            logger.info("课程绩效|待统计数量为0，统计结束");
        }
    }

    public void paperAssessTask() {
        String regex = PAPER_ASSESS_KEY + "*";
        Set<String> keySet = paperRedisTemplate.keys(regex);
        if (keySet != null && !keySet.isEmpty()) {
            ParamSearchModel searchModel = new ParamSearchModel();
            searchModel.setcType(RuleSettingCTypeEnum.PAPER.getCode());
            List<AssessRule> assessRuleList = assessRuleService.getAssessList(searchModel);
            List<PaperModel> list = paperRedisTemplate.opsForValue().multiGet(keySet);
            if (list != null && !list.isEmpty()) {
                AtomicInteger count = new AtomicInteger();
                list.forEach(each -> {
                    if (paperScoreService.hasScoreChecked(each.getId())) {
                        paperRedisTemplate.delete(PAPER_ASSESS_KEY + each.getId().toString());
                        logger.info("论文绩效|[courseId: {}]已存在统计数据，删除缓存", each.getId());
                    } else {
                        count.getAndIncrement();
                        commonThreadPool.execute(new CalculateTaskThread(RuleSettingCTypeEnum.PAPER,
                                JacksonUtil.object2Map(each),
                                assessRuleList,
                                paramsRulesSettingService,
                                ruleSettingService,
                                assessmentService,
                                assessRuleService,
                                courseScoreService,
                                paperScoreService,
                                internScoreService
                        ));
                    }
                });
            } else {
                logger.info("论文绩效|待统计数量为0，统计结束");
            }
        } else {
            logger.info("论文绩效|待统计数量为0，统计结束");
        }
    }

    public void internAssessTask() {
        String regex = INTERN_ASSESS_KEY + "*";
        Set<String> keySet = internRedisTemplate.keys(regex);
        if (keySet != null && !keySet.isEmpty()) {
            ParamSearchModel searchModel = new ParamSearchModel();
            searchModel.setcType(RuleSettingCTypeEnum.PAPER.getCode());
            List<AssessRule> assessRuleList = assessRuleService.getAssessList(searchModel);
            List<InternModel> list = internRedisTemplate.opsForValue().multiGet(keySet);
            if (list != null && !list.isEmpty()) {
                AtomicInteger count = new AtomicInteger();
                list.forEach(each -> {
                    if (internScoreService.hasScoreChecked(each.getId())) {
                        internRedisTemplate.delete(PAPER_ASSESS_KEY + each.getId().toString());
                        logger.info("实习绩效|[internId: {}]已存在统计数据，删除缓存", each.getId());
                    } else {
                        count.getAndIncrement();
                        commonThreadPool.execute(new CalculateTaskThread(RuleSettingCTypeEnum.INTERN,
                                JacksonUtil.object2Map(each),
                                assessRuleList,
                                paramsRulesSettingService,
                                ruleSettingService,
                                assessmentService,
                                assessRuleService,
                                courseScoreService,
                                paperScoreService,
                                internScoreService
                        ));
                    }
                });
            } else {
                logger.info("实习绩效|待统计数量为0，统计结束");
            }
        } else {
            logger.info("实习绩效|待统计数量为0，统计结束");
        }
    }

    static class CalculateTaskThread implements Runnable {

        private final Logger logger = LoggerFactory.getLogger(CalculateTaskThread.class);

        private final Map<String, Object> data;
        private final ParamsRulesSettingService paramsRulesSettingService;
        private final RuleSettingService ruleSettingService;
        private final AssessmentService assessmentService;
        private final AssessRuleService assessRuleService;
        private final CourseScoreService courseScoreService;
        private final PaperScoreService paperScoreService;
        private final InternScoreService internScoreService;
        private final List<AssessRule> assessRuleList;
        private final RuleSettingCTypeEnum cTypeEnum;

        public CalculateTaskThread(RuleSettingCTypeEnum cTypeEnum, Map<String, Object> data, List<AssessRule> assessRuleList, ParamsRulesSettingService paramsRulesSettingService, RuleSettingService ruleSettingService, AssessmentService assessmentService, AssessRuleService assessRuleService, CourseScoreService courseScoreService, PaperScoreService paperScoreService, InternScoreService internScoreService) {
            this.cTypeEnum = cTypeEnum;
            this.data = data;
            this.assessRuleList = assessRuleList;
            this.paramsRulesSettingService = paramsRulesSettingService;
            this.ruleSettingService = ruleSettingService;
            this.assessmentService = assessmentService;
            this.assessRuleService = assessRuleService;
            this.courseScoreService = courseScoreService;
            this.paperScoreService = paperScoreService;
            this.internScoreService = internScoreService;
        }

        @Override
        public void run() {
            // 筛选需要进行统计的assess
            logger.info("绩效统计|统计开始，统计类型: {}, 统计id: {}", cTypeEnum.getValue(), data.getOrDefault("id", ""));
            List<AssessRule> assessRuleForData = new ArrayList<>();
            Set<Long> ruleId = new HashSet<>();
            assessRuleList.forEach(eachAssess -> {
                if (!StringUtils.isBlank(eachAssess.getRuleSettingIds())) {
                    List<Long> ruleIdList = AssessRuleUtil.parseIdList(eachAssess.getRuleSettingIds());
                    List<RuleSetting> ruleList = ruleSettingService.queryByIdList(ruleIdList);
                    List<Long> checkedRuleId = AssessRuleUtil.checkRules(data, ruleList);
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
                calculateAssessRules(data, assessRuleForData, ruleId, cTypeEnum);
            }
            logger.info("绩效统计|统计结束，统计类型: {}, 统计id: {}", cTypeEnum.getValue(), data.getOrDefault("id", ""));
        }

        /**
         * 统计一个工作量对应的绩效
         *
         * @author dude
         * @date 2021/3/5
         **/
        void calculateAssessRules(Map<String, Object> data, List<AssessRule> assessRuleList, Set<Long> ruleId, RuleSettingCTypeEnum cType) {
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
                        List<ParamsRulesSetting> paramsRulesList = paramsRulesSettingService.queryByCNumList(cNumForParse);
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
                        List<RuleSetting> ruleForCheck = ruleSettingService.queryByIdList(ruleIdForCheck);
                        ruleId.addAll(AssessRuleUtil.checkRules(data, ruleForCheck));
                        paramValueMap.putAll(AssessRuleUtil.selectParamValue(data, paramsRulesList, ruleId));
                    }
                    assessFormat = AssessRuleUtil.parseFormat(assessRule.getAssessDetail(), paramValueMap);
                }
                assessScore = AssessRuleUtil.calculate(assessFormat);
                // 插入新的分数统计
                switch (cType) {
                    case COURSE:{
                        CourseScore courseScore = new CourseScore();
                        courseScore.setAdminId(Long.parseLong(data.getOrDefault("adminId", 0L).toString()));
                        courseScore.setCourseHoursId(Long.valueOf(data.getOrDefault("id", 0).toString()));
                        courseScore.setTeacherId(assessment.getTeacherId());
                        courseScore.setSchoolYear(assessment.getSchoolYear());
                        courseScore.setSemester(assessment.getSemester());
                        courseScore.setAssessRuleId(assessRule.getId());
                        courseScore.setAssessDetail(assessRule.getAssessDetail());
                        courseScore.setAssessFormat(assessFormat);
                        courseScore.setTotal(assessScore);
                        assessment.setCourseQuality(assessment.getCourseQuality().add(assessScore));
                        courseScoreService.updateOrInsert(courseScore);
                        break;
                    }
                    case INTERN:{
                        InternScore internScore = new InternScore();
                        internScore.setAdminId(Long.parseLong(data.getOrDefault("adminId", 0L).toString()));
                        internScore.setInternId(Long.valueOf(data.getOrDefault("id", 0).toString()));
                        internScore.setTeacherId(assessment.getTeacherId());
                        internScore.setSchoolYear(assessment.getSchoolYear());
                        internScore.setSemester(assessment.getSemester());
                        internScore.setAssessRuleId(assessRule.getId());
                        internScore.setAssessDetail(assessRule.getAssessDetail());
                        internScore.setAssessFormat(assessFormat);
                        internScore.setTotal(assessScore);
                        assessment.setInternQuality(assessment.getInternQuality().add(assessScore));
                        internScoreService.updateOrInsert(internScore);
                        break;
                    }
                    case PAPER:{
                        PaperScore paperScore = new PaperScore();
                        paperScore.setAdminId(Long.parseLong(data.getOrDefault("adminId", 0L).toString()));
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
                        paperScoreService.updateOrInsert(paperScore);
                        break;
                    }
                }
            }
            assessment.setTotalQuality(assessment.getCourseQuality()
                    .add(assessment.getPaperQuality())
                    .add(assessment.getInternQuality())
                    .add(assessment.getOtherQuality()));
            assessmentService.updateOrInsert(assessment, cType.getCode());
        }

    }

}
