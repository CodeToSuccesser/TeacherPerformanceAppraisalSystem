package com.business.tpas.model;

import com.business.tpas.enums.RuleSettingCTypeEnum;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname ScoreAnalysisSerieModel
 * @description 汇总分析数据
 * @date 2021/4/7
 **/
@ApiModel(value = "汇总分析数据model")
public class ScoreAnalysisSerieModel {

    private Integer cType;

    private String name;

    private String type;

    private String stack;

    private Integer barMaxWidth;

    private String barGap;

    private String color;

    private String textColor;

    private String position;

    private Integer barBorderRadius;

    private Integer symbolSize;

    private String symbol;

    private List<BigDecimal> data;

    public ScoreAnalysisSerieModel(Integer cType) {
        this.cType = cType;
        RuleSettingCTypeEnum cTypeEnum = RuleSettingCTypeEnum.getEnumByCode(cType);
        if (cTypeEnum == null) {
            this.name = "总分";
            this.type = "line";
            this.stack = "total";
            this.symbolSize = 10;
            this.symbol = "circle";
            this.color = "rgba(252,230,48,1)";
            this.barBorderRadius = 0;
            this.position = "top";
            return;
        }
        switch (cTypeEnum) {
            case COURSE:{
                this.name = RuleSettingCTypeEnum.COURSE.getValue();
                this.type = "bar";
                this.stack = "total";
                this.barMaxWidth = 35;
                this.barGap = "10%";
                this.color = "rgba(255,144,128,1)";
                this.textColor = "#fff";
                this.position = "insideTop";
                break;
            }
            case PAPER: {
                this.name = RuleSettingCTypeEnum.getEnumByCode(cType).getValue();
                this.type = "bar";
                this.stack = "total";
                this.color = "rgba(0,191,183,1)";
                this.barBorderRadius = 0;
                this.position = "insideTop";
                break;
            }
            case INTERN: {
                this.name = RuleSettingCTypeEnum.getEnumByCode(cType).getValue();
                this.type = "bar";
                this.stack = "total";
                this.color = "rgb(57,127,172)";
                this.barBorderRadius = 0;
                this.position = "insideTop";
                break;
            }
            default: {
            }
        }
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public Integer getBarMaxWidth() {
        return barMaxWidth;
    }

    public void setBarMaxWidth(Integer barMaxWidth) {
        this.barMaxWidth = barMaxWidth;
    }

    public String getBarGap() {
        return barGap;
    }

    public void setBarGap(String barGap) {
        this.barGap = barGap;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getBarBorderRadius() {
        return barBorderRadius;
    }

    public void setBarBorderRadius(Integer barBorderRadius) {
        this.barBorderRadius = barBorderRadius;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<BigDecimal> getData() {
        return data;
    }

    public void setData(List<BigDecimal> data) {
        this.data = data;
    }
}
