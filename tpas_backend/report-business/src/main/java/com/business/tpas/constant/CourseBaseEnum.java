package com.business.tpas.constant;

/**
 * @classname CourseBaseEnum
 * @description 课程信息枚举类
 **/
public enum CourseBaseEnum {

    COMPULSORY("必修课", 0),
    ELECTIVE("选修课", 1),
    PROFESSIONAL_DIRECTION("专业方向课程", 0),
    PROFESSIONAL_CORE("专业核心课程", 1),
    TEACHER_EDUCATION("教师教育课程", 2),
    PRACTICE_TEACHING("实践教学", 3),
    GENERAL_EDUCATION("学科大类课程", 4),
    IS_BILINGUAL("是", 0),
    NOT_BILINGUAL("否", 1),
    SOFT_COURSE("软件", 0),
    HARD_COURSE("硬件", 1),
    UNDERGRADUATES("本科生", 0),
    JUNIOR_COLLEGE_STUDENT("专科生", 1),
    GRADUATE_STUDENT("研究生", 2),
    DEFAULT("", 0);

    public String key;
    public int value;

    CourseBaseEnum(String key, int value){
        this.key = key;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
