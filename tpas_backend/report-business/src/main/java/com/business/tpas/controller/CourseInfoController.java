package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.business.tpas.listener.CourseBaseUploadListener;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.CourseInfoSearchModel;
import com.business.tpas.service.CourseBaseService;
import com.business.tpas.utils.FileUtil;
import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @description 管理员课程管理相关API
 * @date 2021/1/24
 **/
@RestController
@RequestMapping("/course/info")
@Api(tags = {"管理员课程管理相关接口"})
public class CourseInfoController {

    private static final Logger logger = LoggerFactory.getLogger(CourseInfoController.class);

    private static final String contentType = "application/vnd.ms-excel";
    private static final String filePath = "template/课程信息模板.xls";

    @Autowired
    private CourseBaseService courseBaseService;

    /**
     * @description 导入课程管理文件模板
     */
    @ApiOperation(value = "下载课程管理文件模板", notes = "下载课程管理文件模板")
    @GetMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            Resource resource = new ClassPathResource(filePath);
            File file = resource.getFile();
            if (!file.exists()) {
                throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
            }

            response.setContentType(contentType);
            response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            FileUtil.downloadFile(response, file);

        } catch (IOException e) {
            logger.error("fail to download course info upload template", e);
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
        }
    }

    /**
     * @description 导入课程信息文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "导入课程信息文件", notes = "导入课程信息文件")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok"),
        @ApiResponse(code = 500, message = "系统错误")})
    public BaseResponse<?> uploadCourseInfo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("upload file is empty");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
        }
        try {
            EasyExcel
                .read(file.getInputStream(), CourseBaseModel.class, new CourseBaseUploadListener(courseBaseService))
                .sheet().doRead();
        } catch (IOException e) {
            logger.error("fail to upload course info file", e);
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
        }
        return new BaseResponse<>();
    }


    @ApiOperation(value = "根据条件查询课程信息", notes = "根据条件查询课程信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = CourseBaseModel .class),
        @ApiResponse(code = 500, message = "系统错误")})
    @GetMapping("/getCourseInfo")
    public BaseResponse<?> getCourseInfo(@RequestBody CourseInfoSearchModel searchModel) {
        PageInfo<CourseBaseModel> courseBaseModelPage = courseBaseService.getCourseBaseInfo(searchModel);
        return new BaseResponse<>(courseBaseModelPage);
    }
}