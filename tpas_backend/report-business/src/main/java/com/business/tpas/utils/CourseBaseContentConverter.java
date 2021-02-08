package com.business.tpas.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.business.tpas.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dude
 * @version 1.0
 * @classname EasyExcelContentCoverUtil
 * @description easyexcel表内容转换
 * @date 2021/1/31
 **/
public class CourseBaseContentConverter implements Converter<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(CourseBaseContentConverter.class);

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
            case "isBilingual": {
                return IsBilingualEnum.getEnumByValue(stringValue).getCode();
            }
            case "softHard": {
                return SoftHardEnum.getEnumByValue(stringValue).getCode();
            }
            case "studentType": {
                return StudentTypeEnum.getEnumByValue(stringValue).getCode();
            }
            default: {
                return 0;
            }
        }
    }
    /**
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
        String strVal = "";
        switch (contentProperty.getField().getName()) {
            case "courseCharacter": {
                strVal = CourseCharacterEnum.getEnumByCode(value).getValue();
                break;
            }
            case "courseType": {
                strVal = CourseTypeEnum.getEnumByCode(value).getValue();
                break;
            }
            case "isBilingual": {
                strVal = IsBilingualEnum.getEnumByCode(value).getValue();
                break;
            }
            case "softHard": {
                strVal = SoftHardEnum.getEnumByCode(value).getValue();
                break;
            }
            case "studentType": {
                strVal = StudentTypeEnum.getEnumByCode(value).getValue();
                break;
            }
            default: {
                logger.warn(String.format("unknown property: [%s]  during converting data to excel",
                    contentProperty.getField().getName()));
            }
        }
        return new CellData(strVal);
    }
}