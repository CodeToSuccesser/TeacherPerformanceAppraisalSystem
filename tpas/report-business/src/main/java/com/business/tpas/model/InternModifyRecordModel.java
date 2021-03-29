package com.business.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 实习带队信息修改审批记录
 **/
@ApiModel(value = "实习带队信息修改审批记录")
public class InternModifyRecordModel {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 实习指导信息编码
     */
    @ApiModelProperty(value = "实习指导信息编码")
    private Long internId;

    /**
     * 师范实习带队人数
     */
    @ApiModelProperty(value = "师范实习带队人数")
    private Integer normalPractice;

    /**
     * 非师范实习带队人数
     */
    @ApiModelProperty(value = "非师范实习带队人数")
    private Integer nonNormalPractice;

    /**
     * 校内实习带队人数
     */
    @ApiModelProperty(value = "校内实习带队人数")
    private Integer schoolPractice;

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

    /**
     * 逻辑删除标识
     */
    @ApiModelProperty(value = "逻辑删除标识")
    private Integer isDeleted;

    /**
     * 修改师范实习带队人数
     */
    @ApiModelProperty(value = "修改师范实习带队人数")
    private Integer modifyNormalPractice;

    /**
     * 修改非师范实习带队人数
     */
    @ApiModelProperty(value = "修改非师范实习带队人数")
    private Integer modifyNonNormalPractice;

    /**
     * 修改校内实习带队人数
     */
    @ApiModelProperty(value = "修改校内实习带队人数")
    private Integer modifySchoolPractice;

    /**
     * 修改学期
     */
    @ApiModelProperty(value = "修改学期")
    private Integer modifySemester;

    /**
     * 修改学年
     */
    @ApiModelProperty(value = "修改学年")
    private String modifySchoolYear;

    /**
     * 提出修改用户类型, 默认0 教师, 1 管理员
     */
    @ApiModelProperty(value = "提出修改用户类型, 默认0 教师, 1 管理员")
    private Integer applyType;

    /**
     * 提出修改用户编码, teachar_id或者admin_id
     */
    @ApiModelProperty(value = "提出修改用户编码, teachar_id或者admin_id")
    private Long applyId;

    @ApiModelProperty(value = "提出修改用户编码")
    private Long teacherCode;

    @ApiModelProperty(value = "提出修改用户名")
    private Long applyName;

    /**
     * 处理的管理员编码
     */
    @ApiModelProperty(value = "处理的管理员编码")
    private Long adminId;

    /**
     * 管理员处理时间
     */
    @ApiModelProperty(value = "管理员处理时间")
    private Date checkTime;

    /**
     * 管理员处理结果, 默认0 未处理, 1 通过, 2 驳回
     */
    @ApiModelProperty(value = " 管理员处理结果, 默认0 未处理, 1 通过, 2 驳回")
    private Integer checkResult;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据最新操作时间
     */
    @ApiModelProperty(value = "数据最新操作时间")
    private Date updateTime;

    @ApiModelProperty(value = "记录新增时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInternId() {
        return internId;
    }

    public void setInternId(Long internId) {
        this.internId = internId;
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

    public Integer getModifyNormalPractice() {
        return modifyNormalPractice;
    }

    public void setModifyNormalPractice(Integer modifyNormalPractice) {
        this.modifyNormalPractice = modifyNormalPractice;
    }

    public Integer getModifyNonNormalPractice() {
        return modifyNonNormalPractice;
    }

    public void setModifyNonNormalPractice(Integer modifyNonNormalPractice) {
        this.modifyNonNormalPractice = modifyNonNormalPractice;
    }

    public Integer getModifySchoolPractice() {
        return modifySchoolPractice;
    }

    public void setModifySchoolPractice(Integer modifySchoolPractice) {
        this.modifySchoolPractice = modifySchoolPractice;
    }

    public Integer getModifySemester() {
        return modifySemester;
    }

    public void setModifySemester(Integer modifySemester) {
        this.modifySemester = modifySemester;
    }

    public String getModifySchoolYear() {
        return modifySchoolYear;
    }

    public void setModifySchoolYear(String modifySchoolYear) {
        this.modifySchoolYear = modifySchoolYear;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
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

    public Long getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(Long teacherCode) {
        this.teacherCode = teacherCode;
    }

    public Long getApplyName() {
        return applyName;
    }

    public void setApplyName(Long applyName) {
        this.applyName = applyName;
    }

    @Override
    public String toString() {
        return "InternModifyRecordModel{" + "id=" + id + ", internId=" + internId + ", normalPractice=" + normalPractice
            + ", nonNormalPractice=" + nonNormalPractice + ", schoolPractice=" + schoolPractice + ", semester="
            + semester + ", schoolYear=" + schoolYear + ", isDeleted=" + isDeleted + ", modifyNormalPractice="
            + modifyNormalPractice + ", modifyNonNormalPractice=" + modifyNonNormalPractice + ", modifySchoolPractice="
            + modifySchoolPractice + ", modifySemester=" + modifySemester + ", modifySchoolYear=" + modifySchoolYear
            + ", applyType=" + applyType + ", applyId=" + applyId
            + ", adminId=" + adminId + ", checkTime=" + checkTime + ", checkResult=" + checkResult + ", remark='"
            + remark + '\'' + ", updateTime=" + updateTime + ", createTime=" + createTime + '}';
    }
}
