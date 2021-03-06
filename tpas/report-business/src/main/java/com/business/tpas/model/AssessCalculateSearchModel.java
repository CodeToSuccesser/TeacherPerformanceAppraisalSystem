package com.business.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname AssessCalculateSearchModel
 * @description 统计绩效选择
 * @date 2021/3/5
 **/
@ApiModel("统计绩效选择")
public class AssessCalculateSearchModel {

    @ApiModelProperty(value = "权值类型, 1 授课, 2 指导论文, 3 其他")
    private Integer cType;

    @ApiModelProperty(value = "学期, 默认1 第一学期, 2 第二学期")
    private Integer semester;

    @ApiModelProperty(value = "学年")
    private String schoolYear;

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    @Override
    public String toString() {
        return "AssessCalculateSearchModel{" +
                "cType=" + cType +
                ", semester=" + semester +
                ", schoolYear='" + schoolYear + '\'' +
                '}';
    }
}
