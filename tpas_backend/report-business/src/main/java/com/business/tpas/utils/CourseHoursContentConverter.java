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
 * @description easyExcel表格内容转换 课时信息内容转换
 **/
public class CourseHoursContentConverter implements Converter<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(CourseHoursContentConverter.class);

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 读入时候转换
     * 每个单元格都会调用
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    /**
     * 写出时转换
     * 每个单元格都会调用
     * @param value
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        String strVal = "";
        switch (contentProperty.getField().getName()) {
            case "semester": {
                strVal = SemesterEnum.getEnumByCode(value).getValue();
                break;
            }
            case "primarySecondary": {
                strVal = PrimarySecondaryEnum.getEnumByCode(value).getValue();
                break;
            }
            default: {
                logger.warn(String.format("unknown property: [%s]  during converting course hours data to excel",
                    contentProperty.getField().getName()));
            }
        }
        return new CellData(strVal);
    }
}
