package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRoleModel
 * @description 系统角色
 * @date 2021/3/10
 **/
@ApiModel("系统角色")
public class SystemRoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色对于菜单目录权限，用逗号拼接，如：memuA,menuB1")
    private String menusValue;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单全值，从顶级菜单到本身，竖线'|'分割")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenusValue() {
        return menusValue;
    }

    public void setMenusValue(String menusValue) {
        this.menusValue = menusValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SystemRoleModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menusValue='" + menusValue + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
