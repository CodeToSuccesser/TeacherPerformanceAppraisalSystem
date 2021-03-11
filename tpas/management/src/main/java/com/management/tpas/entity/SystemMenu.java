package com.management.tpas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname SystemMenu
 * @description 系统路由菜单权限表
 * @date 2021/3/10
 **/
@ApiModel("系统路由菜单权限表")
public class SystemMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id = 0L;

    @ApiModelProperty(value = "菜单值")
    private String value;

    @ApiModelProperty(value = "菜单标示，web页面显示内容")
    private String label;

    @ApiModelProperty(value = "控制路径，web页面路由路径")
    private String path;

    @ApiModelProperty(value = "图标类型")
    private String iconType;

    @ApiModelProperty(value = "菜单等级，0 顶级菜单，依次递增")
    private Integer level;

    @ApiModelProperty(value = "父级菜单值")
    private String parentValue;

    @ApiModelProperty(value = "父级菜单值")
    private String fullValue;

    @ApiModelProperty(value = "菜单全值，从顶级菜单到本身，竖线'|'分割")
    private Date createTime;

    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getFullValue() {
        return fullValue;
    }

    public void setFullValue(String fullValue) {
        this.fullValue = fullValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    @Override
    public String toString() {
        return "SystemMenu{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", path='" + path + '\'' +
                ", iconType='" + iconType + '\'' +
                ", level='" + level + '\'' +
                ", parentValue='" + parentValue + '\'' +
                ", fullValue='" + fullValue + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }


}
