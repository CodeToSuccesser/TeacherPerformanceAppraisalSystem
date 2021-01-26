package com.business.tpas.constant;

/**
 * @description 课程类别, 专业方向,专业核心,教师教育,实践教学,大类教育
 **/
public enum CourseTypeEnum {


    PROFESSIONAL_DIRECTION("专业方向课程", 0),
    PROFESSIONAL_CORE("专业核心课程", 1),
    TEACHER_EDUCATION("教师教育课程", 2),
    PRACTICE_TEACHING("实践教学", 3),
    GENERAL_EDUCATION("学科大类课程", 4);

    public String key;
    public int value;

    CourseTypeEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
