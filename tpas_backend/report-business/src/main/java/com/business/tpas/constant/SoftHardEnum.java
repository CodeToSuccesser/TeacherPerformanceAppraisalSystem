package com.business.tpas.constant;

/**
 * @description 软硬件课程, 软件, 硬件
 **/
public enum SoftHardEnum {
    SOFT_COURSE("软件", 0),
    HARD_COURSE("硬件", 1);

    public String key;
    public int value;

    SoftHardEnum(String key, int value) {
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
