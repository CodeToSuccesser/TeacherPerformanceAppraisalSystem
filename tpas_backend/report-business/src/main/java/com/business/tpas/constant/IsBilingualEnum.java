package com.business.tpas.constant;

/**
 * @description 否双语授课, 是, 否
 **/
public enum IsBilingualEnum {

    IS_BILINGUAL("是", 0),
    NOT_BILINGUAL("否", 1);

    public String key;
    public int value;

    IsBilingualEnum(String key, int value) {
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
