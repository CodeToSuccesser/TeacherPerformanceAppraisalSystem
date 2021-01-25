package com.business.tpas.constant;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @classname CourseBaseMap
 * @description 课程基本信息枚举映射类
 **/
public class CourseBaseMap {

    public static Map<String, CourseBaseEnum> courseBaseEnumMap = new HashMap<>();

    @PostConstruct
    public void initMap(){
        courseBaseEnumMap.put(CourseBaseEnum.COMPULSORY.key, CourseBaseEnum.COMPULSORY);
        courseBaseEnumMap.put(CourseBaseEnum.ELECTIVE.key, CourseBaseEnum.ELECTIVE);
        courseBaseEnumMap.put(CourseBaseEnum.GENERAL_EDUCATION.key, CourseBaseEnum.GENERAL_EDUCATION);
        courseBaseEnumMap.put(CourseBaseEnum.GRADUATE_STUDENT.key, CourseBaseEnum.GRADUATE_STUDENT);
        courseBaseEnumMap.put(CourseBaseEnum.HARD_COURSE.key, CourseBaseEnum.HARD_COURSE);
        courseBaseEnumMap.put(CourseBaseEnum.IS_BILINGUAL.key, CourseBaseEnum.IS_BILINGUAL);
        courseBaseEnumMap.put(CourseBaseEnum.JUNIOR_COLLEGE_STUDENT.key, CourseBaseEnum.JUNIOR_COLLEGE_STUDENT);
        courseBaseEnumMap.put(CourseBaseEnum.NOT_BILINGUAL.key, CourseBaseEnum.NOT_BILINGUAL);
        courseBaseEnumMap.put(CourseBaseEnum.PRACTICE_TEACHING.key, CourseBaseEnum.PRACTICE_TEACHING);
        courseBaseEnumMap.put(CourseBaseEnum.PROFESSIONAL_CORE.key, CourseBaseEnum.PROFESSIONAL_CORE);
        courseBaseEnumMap.put(CourseBaseEnum.PROFESSIONAL_DIRECTION.key, CourseBaseEnum.PROFESSIONAL_DIRECTION);
        courseBaseEnumMap.put(CourseBaseEnum.SOFT_COURSE.key, CourseBaseEnum.SOFT_COURSE);
        courseBaseEnumMap.put(CourseBaseEnum.TEACHER_EDUCATION.key, CourseBaseEnum.TEACHER_EDUCATION);
        courseBaseEnumMap.put(CourseBaseEnum.UNDERGRADUATES.key, CourseBaseEnum.UNDERGRADUATES);
    }
}
