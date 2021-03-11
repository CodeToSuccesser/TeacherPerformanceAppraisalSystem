package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname RouterMenus
 * @description 目录菜单
 * @date 2021/3/11
 **/
@ApiModel("目录菜单")
public class RouterMenus {

    @ApiModelProperty(value = "")
    private String path;

    @ApiModelProperty(value = "")
    private Boolean alwaysShow;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private Meta meta;

    @ApiModelProperty(value = "")
    private List<RouterMenus> children;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<RouterMenus> getChildren() {
        return children;
    }

    public void setChildren(List<RouterMenus> children) {
        this.children = children;
    }

    public RouterMenus() {
        this.alwaysShow = null;
    }
}