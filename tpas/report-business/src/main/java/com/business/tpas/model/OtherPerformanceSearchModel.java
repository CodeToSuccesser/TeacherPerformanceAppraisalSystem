package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description 其他教务工作信息查找类
 **/
public class OtherPerformanceSearchModel extends PageModel {

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

    /**
     * 工作标题
     */
    @ApiModelProperty(value = "工作标题")
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
