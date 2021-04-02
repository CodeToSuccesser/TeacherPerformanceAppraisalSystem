package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname ScoreSearchModel
 * @description 分数查询类
 * @date 2021/4/3
 **/
@ApiModel(value = "分数查询类")
public class ScoreSearchModel extends PageModel {

    @ApiModelProperty(value = "教师编码")
    private String teacherCode;

    @ApiModelProperty(value = "学期")
    private Integer semester;

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
