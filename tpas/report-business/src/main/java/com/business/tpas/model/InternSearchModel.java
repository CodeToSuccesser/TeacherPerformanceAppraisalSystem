package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description 实习指导信息查询条件类
 **/
public class InternSearchModel extends PageModel {

    @ApiModelProperty(value = "教师编码")
    private String teacherCode;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    @ApiModelProperty(value = "学期")
    private Integer semester;

    /**
     * 学年
     */
    @ApiModelProperty(value = "学年")
    private String schoolYear;

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
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
}
