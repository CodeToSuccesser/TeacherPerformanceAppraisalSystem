package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname Meta
 * @description mata信息
 * @date 2021/3/11
 **/
@ApiModel("mata信息")
public class Meta {

    @ApiModelProperty(value = "")
    private String title;

    @ApiModelProperty(value = "")
    private String icon;

    @ApiModelProperty(value = "")
    private List<String> roles;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
