package com.business.tpas.enums;

/**
 * @author dude
 * @version 1.0
 * @classname RuleSettingColumnNameEnum
 * @description 权值规则字段
 * @date 2021/3/1
 **/
public enum RuleSettingColumnNameEnum {

    COURSE_CHARACTER(1, "course_character", "课程性质, 默认0 必修, 1 选修"),
    COURSE_TYPE(1, "course_type", "课程类别, 默认0 专业方向, 1 专业核心, 2 教师教育, 3 实践教学, 4 大类教育"),
    COURSE_CREDIT(1, "course_cridet", "学分"),
    TOTAL_HOURS(1, "total_hours", "总学时数"),
    IS_BILINGUAL(1, "is_bilingual", "是否双语授课, 默认0 非双语, 1 双语"),
    SOFT_HARD(1, "soft_hard", "软硬件课程, 默认0 软件, 1 硬件"),
    STUDENT_TYPE(1, "student_type", "学生类型, 默认0 本科生, 1 专科生, 2 研究生"),
    STUDENT_NUMBER(1, "student_number", "课程人数, 默认0"),
    TOTAL_CAPACITY(1, "total_capacity", "课程总容量, 默认0"),
    SELECTED_STUDENT(1, "selected_student", "已选学生数, 默认0"),
    TEACHING_HOURS(1, "teaching_hours", "讲课学时, 默认0"),
    COMPUTER_HOURS(1, "computer_hours", "上机学时, 默认0"),
    EXPERIMENT_HOURS(1, "experiment_hours", "实验学时, 默认0"),
    PRIMARY_SECONDARY(1, "primary_secondary", "主讲辅讲标记, 默认0 主讲, 1 辅讲"),
    EXP_NUMBER(1, "exp_number", "实验批数, 默认1"),
    EXP_PER_NUMBER(1, "exp_per_number", "实验每批次人数, 默认0"),
    PAPER_STUDENT_NUMBER(2, "student_number", "指导学生人数, 默认0"),
    NORMAL_PRACTICE(3, "normal_practice", "师范实习带队人数, 默认0"),
    NON_NORMAL_PRACTICE(3, "non_normal_practice", "非师范实习带队人数, 默认0"),
    SCHOOL_PRACTICE(3, "school_practice", "校内实习带队人数, 默认0")
    ;

    private final Integer cType;

    private final String columnName;

    private final String remark;

    public Integer getcType() {
        return cType;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getRemark() {
        return remark;
    }

    RuleSettingColumnNameEnum(Integer cType, String columnName, String remark) {
        this.cType = cType;
        this.columnName = columnName;
        this.remark = remark;
    }
}
