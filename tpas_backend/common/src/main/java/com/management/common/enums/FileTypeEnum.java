package com.management.common.enums;


/**
 * @author dude
 * @version 1.0
 * @classname FileTypeEnum
 * @description 文件数据类型
 * @date 2021/2/8
 **/
public enum FileTypeEnum {

    IMAGE(0, "图片");

    public final int code;

    public final String value;

    FileTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
