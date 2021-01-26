package com.business.tpas.constant;

/**
 * @description 课程性质, 必修课, 选修课
 **/
public enum CourseCharacterEnum {

    COMPULSORY("必修课", 0),
    ELECTIVE("选修课", 1);

    public String key;
    public int value;

    CourseCharacterEnum(String key, int value) {
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
