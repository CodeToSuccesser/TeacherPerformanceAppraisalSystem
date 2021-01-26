package com.business.tpas.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @classname CourseBaseMap
 * @description 课程基本信息枚举映射类
 **/
public class CourseBaseMap {

    public static Map<String, Integer> courseBaseMap = new HashMap<>();

    static {
        courseBaseMap.put(CourseCharacterEnum.COMPULSORY.key, CourseCharacterEnum.COMPULSORY.value);
        courseBaseMap.put(CourseCharacterEnum.ELECTIVE.key, CourseCharacterEnum.ELECTIVE.value);
        courseBaseMap.put(CourseTypeEnum.GENERAL_EDUCATION.key, CourseTypeEnum.GENERAL_EDUCATION.value);
        courseBaseMap.put(StudentTypeEnum.GRADUATE_STUDENT.key, StudentTypeEnum.GRADUATE_STUDENT.value);
        courseBaseMap.put(SoftHardEnum.HARD_COURSE.key, SoftHardEnum.HARD_COURSE.value);
        courseBaseMap.put(IsBilingualEnum.IS_BILINGUAL.key, IsBilingualEnum.IS_BILINGUAL.value);
        courseBaseMap.put(StudentTypeEnum.JUNIOR_COLLEGE_STUDENT.key, StudentTypeEnum.JUNIOR_COLLEGE_STUDENT.value);
        courseBaseMap.put(IsBilingualEnum.NOT_BILINGUAL.key, IsBilingualEnum.NOT_BILINGUAL.value);
        courseBaseMap.put(CourseTypeEnum.PRACTICE_TEACHING.key, CourseTypeEnum.PRACTICE_TEACHING.value);
        courseBaseMap.put(CourseTypeEnum.PROFESSIONAL_CORE.key, CourseTypeEnum.PROFESSIONAL_CORE.value);
        courseBaseMap.put(CourseTypeEnum.PROFESSIONAL_DIRECTION.key, CourseTypeEnum.PROFESSIONAL_DIRECTION.value);
        courseBaseMap.put(SoftHardEnum.SOFT_COURSE.key, SoftHardEnum.SOFT_COURSE.value);
        courseBaseMap.put(CourseTypeEnum.TEACHER_EDUCATION.key, CourseTypeEnum.TEACHER_EDUCATION.value);
        courseBaseMap.put(StudentTypeEnum.UNDERGRADUATES.key, StudentTypeEnum.UNDERGRADUATES.value);
    }
}
