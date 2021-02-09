package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description 课时记录条件查询模型层
 **/
@ApiModel(value = "课时记录条件查询模型层", description = "课时记录条件查询模型层")
public class CourseHoursSearchModel extends PageModel {

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "学期")
    private Integer semester;

    @ApiModelProperty(value = "学年")
    private Integer schoolYear;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    @Override
    public String toString() {
        return "CourseHoursSearchModel{" + "courseName='" + courseName + '\'' + ", semester=" + semester
            + ", schoolYear=" + schoolYear + '}';
    }
}
