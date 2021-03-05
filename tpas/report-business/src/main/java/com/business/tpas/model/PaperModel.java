package com.business.tpas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.business.tpas.utils.PaperContentConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 论文数据模型层
 **/

@ApiModel(value = "论文数据模型层", description = "论文数据模型层")
public class PaperModel {

    /**
     * 主键
     */
    @ExcelIgnore
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 教师编码
     */
    @ExcelIgnore
    @ApiModelProperty(value = "教师编码")
    private Long teacherId;

    @ApiModelProperty(value = "教师姓名")
    @ExcelProperty(value = "教师姓名")
    private String teacherName;

    @ApiModelProperty(value = "工号")
    @ExcelProperty(value = "工号")
    private String teacherCode;

    /**
     * 专业信息编码
     */
    @ExcelIgnore
    @ApiModelProperty(value = "专业信息编码id")
    private Long majorId;

    @ExcelProperty(value = "校内专业代码")
    @ApiModelProperty(value = "校内专业代码")
    private String majorCode;

    /**
     * 专业名称
     */
    @ExcelProperty(value = "专业名称")
    @ApiModelProperty(value = "专业名称")
    private String majorName;

    /**
     * 指导学生人数, 默认0
     */
    @ExcelProperty(value = "指导专业学生数量")
    @ApiModelProperty(value = "指导学生人数, 默认0")
    private Integer studentNumber;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    @ExcelProperty(value = "学期", converter = PaperContentConverter.class)
    @ApiModelProperty(value = "学期, 默认1 第一学期, 2 第二学期")
    private Integer semester;

    /**
     * 学年
     */
    @ExcelProperty(value = "学年")
    @ApiModelProperty(value = "学年")
    private String schoolYear;

    /**
     * 管理员编码
     */
    @ExcelIgnore
    @ApiModelProperty(value = "管理员编码")
    private Long adminId;

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
    @ApiModelProperty(value = "数据最新操作时间")
    private Date updateTime;

    @ExcelIgnore
    @ApiModelProperty(value = "数据插入时间")
    private Date createTime;

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

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

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

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "PaperModel{" + "id=" + id + ", teacherId=" + teacherId + ", majorId=" + majorId + ", studentNumber="
            + studentNumber + ", semester=" + semester + ", schoolYear=" + schoolYear + ", adminId=" + adminId
            + ", remark='" + remark + '\'' + ", updateTime=" + updateTime + ", createTime=" + createTime + '}';
    }
}
