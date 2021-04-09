package com.business.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname ScoreAnalysisModel
 * @description 考核汇总
 * @date 2021/4/7
 **/
@ApiModel(value = "考核汇总model")
public class ScoreAnalysisModel {

    @ApiModelProperty(value = "纵坐标")
    private List<String> xData;

    @ApiModelProperty(value = "表数据")
    private List<ScoreAnalysisSerieModel> dataZoomSeries;

    @ApiModelProperty(value = "数据类型")
    private List<String> legendData;

    @ApiModelProperty(value = "表头")
    private String title;

    public List<String> getxData() {
        return xData;
    }

    public void setxData(List<String> xData) {
        this.xData = xData;
    }

    public List<ScoreAnalysisSerieModel> getDataZoomSeries() {
        return dataZoomSeries;
    }

    public void setDataZoomSeries(List<ScoreAnalysisSerieModel> dataZoomSeries) {
        this.dataZoomSeries = dataZoomSeries;
    }

    public List<String> getLegendData() {
        return legendData;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
