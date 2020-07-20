package com.management.tpas.model;

import java.io.Serializable;

/**
 * @author dude
 * @version 1.0
 * @classname ExampleModel
 * @description TODO
 * @date 2020/7/20
 **/
public class ExampleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer flag;

    public ExampleModel() {
    }

    public ExampleModel(Integer flag) {
        this.flag = flag;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ExampleModel{" +
                "flag=" + flag +
                '}';
    }
}
