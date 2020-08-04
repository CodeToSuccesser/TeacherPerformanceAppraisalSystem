package com.management.tpas.controller;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.model.BaseResponse;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.model.ExampleModel;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.service.TeacherMsgService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TeacherMsgService teacherMsgService;

    @PostMapping("/test")
    public String myTest(@RequestBody ExampleModel exampleModel) {
        if(null == exampleModel.getFlag()){
            return JacksonUtil.object2Json(new BaseResponse<Object>(ErrorCodeEnum.NO_PERMISSION.code, ErrorCodeEnum.NO_PERMISSION.msg));
        }
        return JacksonUtil.object2Json(new BaseResponse<Object>(exampleModel));
    }

    @PostMapping("/insertTeacher")
    public BaseResponse insertTeacher(@RequestBody TeacherMsgModel teacherMsgModel){
        teacherMsgService.insertTeacherMsg(teacherMsgModel);
        return new BaseResponse();
    }
}
