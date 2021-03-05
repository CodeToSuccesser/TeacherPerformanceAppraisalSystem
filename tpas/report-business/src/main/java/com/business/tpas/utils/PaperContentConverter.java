package com.business.tpas.utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.business.tpas.enums.SemesterEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description easyExcel论文信息转换类
 **/
public class PaperContentConverter implements Converter<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(PaperContentConverter.class);

    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) throws Exception {
        String strVal = "";
        switch (contentProperty.getField().getName()) {
            case "semester": {
                strVal = SemesterEnum.getEnumByCode(value).getValue();
                break;
            }
            default: {
                logger.warn(String.format("unknown property: [%s]  during converting paper info data to excel",
                    contentProperty.getField().getName()));
            }
        }
        return new CellData(strVal);
    }
}
