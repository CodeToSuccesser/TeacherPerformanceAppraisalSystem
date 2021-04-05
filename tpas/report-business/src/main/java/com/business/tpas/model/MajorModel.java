package com.business.tpas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 专业信息模型层
 **/
@ApiModel(value = "专业信息模型层", description = "专业信息模型层")
public class MajorModel {

    /**
     * 主键
     */
    @ExcelIgnore
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作管理员编号
     */
    @ExcelIgnore
    @ApiModelProperty(value = "操作管理员编号")
    private Long adminId;

    /**
     * 专业代码号
     */
    @ExcelProperty(value = "专业代码号")
    @ApiModelProperty(value = "专业代码号")
    private String majorCode;

    /**
     * 专业名称
     */
    @ExcelProperty(value = "专业名称")
    @ApiModelProperty(value = "专业名称")
    private String majorName;

    /**
     * 账号逻辑删除标识, 0 无删除, 1 已删除
     */
    @ExcelIgnore
    @ApiModelProperty(value = "账号逻辑删除标识, 0 无删除, 1 已删除")
    private Integer isDeleted;

    /**
     * 数据最新操作时间
     */
    @ExcelProperty(value = "数据最新操作时间")
    @ApiModelProperty(value = "数据最新操作时间")
    private Date updateTime;

    @ExcelProperty(value = "数据插入时间")
    @ApiModelProperty(value = "数据插入时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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
