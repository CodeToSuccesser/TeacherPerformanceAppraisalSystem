package com.management.tpas.enums;

/**
 * @author dude
 * @version 1.0
 * @classname UserType
 * @description 用户类型枚举
 * @date 2020/8/9
 **/
public enum UserTypeEnum {

    USER_TYPE_TEACHER(0),
    USER_TYPE_ADMIN(1);

    public int flag;

    UserTypeEnum(int flag) {
        this.flag = flag;
    }

    public static UserTypeEnum getByFlag(int flag) {
        for (UserTypeEnum userTypeEnum : values()) {
            if (flag == userTypeEnum.getFlag()) {
                return userTypeEnum;
            }
        }
        return null;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

}
