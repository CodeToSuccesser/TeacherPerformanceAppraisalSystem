package com.business.tpas.constant;

/**
 * @description 课程基本信息enum
 **/
public enum CourseBaseEnum {

    DEFAULT("", 0); // 默认值

    public String key;
    public int value;

    CourseBaseEnum(String key, int value) {
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
