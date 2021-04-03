package com.business.tpas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.business.tpas.utils.InternContentConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 实习带队信息模型层
 **/
@ApiModel(value = "实习带队信息模型层", description = "实习带队信息模型层")
public class InternModel {

    /**
     * 主键
     */
    @ApiModelProperty(value = "id")
    @ExcelIgnore
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教师编码
     */
    @ExcelIgnore
    @ApiModelProperty(value = "教师id")
    private Long teacherId;

    @ExcelProperty(value = "教师编码")
    @ApiModelProperty(value = "教师编码")
    private String teacherCode;

    @ExcelProperty(value = "教师姓名")
    @ApiModelProperty(value = "教师姓名")
    private String teacherName;

    /**
     * 师范实习带队人数, 默认0
     */
    @ExcelProperty(value = "师范带队人数")
    @ApiModelProperty(value = "师范带队人数")
    private Integer normalPractice;

    /**
     * 非师范实习带队人数, 默认0
     */
    @ExcelProperty(value = "非师范带队人数")
    @ApiModelProperty(value = "非师范带队人数")
    private Integer nonNormalPractice;

    /**
     * 校内实习带队人数, 默认0
     */
    @ExcelProperty(value = "校内实习带队人数")
    @ApiModelProperty(value = "校内实习带队人数")
    private Integer schoolPractice;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    @ExcelProperty(value = "学期", converter = InternContentConverter.class)
    @ApiModelProperty(value = "学期")
    private Integer semester;

    /**
     * 学年
     */
    @ExcelProperty(value = "学年")
    @ApiModelProperty(value = "学年")
    private String schoolYear;

    /**
     * 逻辑删除标识, 0 无删除, 1 已删除
     */
    @ExcelIgnore
    @ApiModelProperty(value = "逻辑删除标志")
    private Integer isDeleted;

    @ExcelProperty("师范生校内外实习")
    @ApiModelProperty(value = "师范生校内外实习")
    private Integer isInSchool;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据最新操作时间
     */
    @ExcelIgnore
    @ApiModelProperty(value = "最新操作时间")
    private Date updateTime;

    @ExcelIgnore
    @ApiModelProperty(value = "插入时间")
    private Date createTime;

    @ExcelIgnore
    private Long adminId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public Integer getNormalPractice() {
        return normalPractice;
    }

    public void setNormalPractice(Integer normalPractice) {
        this.normalPractice = normalPractice;
    }

    public Integer getNonNormalPractice() {
        return nonNormalPractice;
    }

    public void setNonNormalPractice(Integer nonNormalPractice) {
        this.nonNormalPractice = nonNormalPractice;
    }

    public Integer getSchoolPractice() {
        return schoolPractice;
    }

    public void setSchoolPractice(Integer schoolPractice) {
        this.schoolPractice = schoolPractice;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsInSchool() {
        return isInSchool;
    }

    public void setIsInSchool(Integer isInSchool) {
        this.isInSchool = isInSchool;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
