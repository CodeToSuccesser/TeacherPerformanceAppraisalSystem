package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname CourseInfoSearchModel
 * @description 课程信息条件查询类
 * @date 2021/1/28
 **/
@ApiModel(value = "课程信息条件查询类", description = "课程信息条件查询类")
public class CourseInfoSearchModel extends PageModel {

    @ApiModelProperty(value = "课程性质, 0 必修, 1 选修")
    private Integer courseCharacter;

    @ApiModelProperty(value = "课程类别, 0 专业方向, 1 专业核心, 2 教师教育, 3 实践教学, 4 大类教育'")
    private Integer courseType;

    @ApiModelProperty(value = "开课学院")
    private String institute;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "软硬件课程, 0 软件, 1 硬件")
    private Integer softHard;

    @ApiModelProperty(value = "学生类型, 0 本科生, 1 专科生, 2 研究生")
    private Integer studentType;

    @ApiModelProperty(value = "是否双语授课,0 非双语, 1 双语")
    private Integer isBilingual;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseCharacter() {
        return courseCharacter;
    }

    public void setCourseCharacter(Integer courseCharacter) {
        this.courseCharacter = courseCharacter;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Integer getSoftHard() {
        return softHard;
    }

    public void setSoftHard(Integer softHard) {
        this.softHard = softHard;
    }

    public Integer getStudentType() {
        return studentType;
    }

    public void setStudentType(Integer studentType) {
        this.studentType = studentType;
    }

    public Integer getIsBilingual() {
        return isBilingual;
    }

    public void setIsBilingual(Integer isBilingual) {
        this.isBilingual = isBilingual;
    }
}
