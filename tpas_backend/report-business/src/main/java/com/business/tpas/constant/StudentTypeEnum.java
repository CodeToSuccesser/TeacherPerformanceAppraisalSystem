package com.business.tpas.constant;

/**
 * @description 学生类型, 本科生,专科生,研究生
 **/
public enum StudentTypeEnum {


    UNDERGRADUATES("本科生", 0),
    JUNIOR_COLLEGE_STUDENT("专科生", 1),
    GRADUATE_STUDENT("研究生", 2);

    public String key;
    public int value;

    StudentTypeEnum(String key, int value) {
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
