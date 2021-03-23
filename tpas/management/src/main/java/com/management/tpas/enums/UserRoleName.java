package com.management.tpas.enums;

import java.util.Arrays;

/**
 * @author dude
 * @version 1.0
 * @classname UserType
 * @description 用户类型枚举
 * @date 2020/8/9
 **/
public enum UserRoleName {

    USER_TYPE_TEACHER(0, "教师角色"),
    USER_TYPE_ADMIN(1, "管理员角色"),
    USER_TYPE_SUPER(2, "全菜单");

    public Integer flag;

    public String info;

    UserRoleName(int flag, String info) {
        this.flag = flag;
        this.info = info;
    }

    public static UserRoleName getEnumByCode(Integer flag) {
        return Arrays.stream(values()).filter(it -> it.flag.equals(flag)).findFirst().orElse(USER_TYPE_TEACHER);
    }

    public static UserRoleName getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.info.equals(value)).findFirst().orElse(USER_TYPE_TEACHER);
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
