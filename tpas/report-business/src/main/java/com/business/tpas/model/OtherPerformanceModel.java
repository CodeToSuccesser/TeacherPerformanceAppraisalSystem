package com.business.tpas.model;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 其他教务工作信息模型层
 **/
@ApiModel(value = "其他教务工作信息模型层", description = "其他教务工作信息模型层")
public class OtherPerformanceModel {

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    /**
     * 任务标题
     */
    @ApiModelProperty(value = "任务标题")
    private String title;

    /**
     * 任务内容描述
     */
    @ApiModelProperty(value = "任务内容描述")
    private String content;

    /**
     * 任务完成评分，百分制
     */
    @ApiModelProperty(value = "任务完成评分")
    private Integer score;

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

    @ApiModelProperty(value = "教师编码")
    private String teacherCode;

    /**
     * 管理员编码
     */
    @ApiModelProperty(value = "管理员编码")
    private String adminCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 账号逻辑删除标识, 0 无删除, 1 已删除
     */
    @ApiModelProperty(value = "账号逻辑删除标识")
    private Integer isDeleted;

    /**
     * 数据最新操作时间
     */
    @ApiModelProperty(value = "数据最新操作时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
