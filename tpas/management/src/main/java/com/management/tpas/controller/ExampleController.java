package com.management.tpas.controller;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.model.BaseResponse;
import com.management.tpas.model.ExampleModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dude
 * @version 1.0
 * @classname MyController
 * @description TODO
 * @date 2020/7/20
 **/
@RestController
public class ExampleController {

    @PostMapping("/test")
    public BaseResponse myTest(@RequestBody ExampleModel exampleModel) {
        if (null == exampleModel.getFlag()) {
            return new BaseResponse<Object>(ErrorCodeEnum.LOGIN_TIME_OUT.code, ErrorCodeEnum.LOGIN_TIME_OUT.msg);
        }
        return new BaseResponse<Object>(exampleModel);
    }

}
