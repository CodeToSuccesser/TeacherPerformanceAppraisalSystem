package com.management.common.config;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.management.common.enums.CourseCharacterEnum;
import com.management.common.enums.CourseTypeEnum;

/**
 * @author dude
 * @version 1.0
 * @classname EasyExcelContentCoverUtil
 * @description easyexcel表内容转换
 * @date 2021/1/31
 **/
public class EasyExcelContentCoverUtil implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }
    /**
     * 这里读的时候会调用
     *
     * @param cellData
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return 枚举值
     */
    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        String stringValue = cellData.getStringValue();
        switch (contentProperty.getField().getName()) {
            case "courseCharacter": {
                return CourseCharacterEnum.getEnumByValue(stringValue).getCode();
            }
            case "courseType": {
                return CourseTypeEnum.getEnumByValue(stringValue).getCode();
            }
            default: {
                return 0;
            }
        }
    }
    /**
     * 这里是写的时候会调用 不用管
     *
     * @param value
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     */
    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        return new CellData(value);
    }
}