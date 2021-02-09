package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description 查找论文信息条件模型层
 **/
@ApiModel(value = "查找论文信息条件模型层", description = "查找论文信息条件模型层")
public class PaperSearchModel extends PageModel {

    /**
     * 教师id
     */
    @ApiModelProperty(value = "教师id")
    private Long teacherId;

    /**
     * 专业id
     */
    @ApiModelProperty(value = "专业id")
    private Long majorId;

    /**
     * 学期
     */
    @ApiModelProperty(value = "学期")
    private Integer semester;

    /**
     * 学年
     */
    @ApiModelProperty(value = "学年")
    private String schoolYear;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
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
        return "PaperSearchModel{" + "teacherId=" + teacherId + ", majorId=" + majorId + ", semester=" + semester
            + ", schoolYear='" + schoolYear + '\'' + '}';
    }
}
