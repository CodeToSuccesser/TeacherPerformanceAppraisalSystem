package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.business.tpas.enums.CourseCharacterEnum;
import com.business.tpas.enums.CourseTypeEnum;
import com.business.tpas.enums.IsBilingualEnum;
import com.business.tpas.enums.StudentTypeEnum;
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
import org.apache.commons.lang3.StringUtils;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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
    private static final String encoding = "UTF-8";

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
                "attachment;filename=" + URLEncoder.encode(file.getName(), encoding));
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

    @ApiOperation(value = "导出课程信息文件", notes = "导出课程信息文件")
    @GetMapping("/export")
    public void exportCourseInfo(HttpServletResponse response, @RequestBody CourseInfoSearchModel searchModel)
        throws IOException {
        List<CourseBaseModel> courseBaseModels = courseBaseService.getCourseBaseInfo(searchModel);

        try {
            response.setContentType(contentType);
            response.setCharacterEncoding(encoding);
            String fileName = URLEncoder.encode("课程信息导出文件", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), CourseBaseModel.class).sheet("课程信息")
                // 设置字段宽度为自动调整
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(courseBaseModels);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "根据条件查询课程信息", notes = "根据条件查询课程信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = CourseBaseModel .class),
        @ApiResponse(code = 500, message = "系统错误")})
    @GetMapping("/getCourseInfo")
    public BaseResponse<?> getCourseInfo(@RequestBody CourseInfoSearchModel searchModel) {
        PageInfo<CourseBaseModel> courseBaseModelPage = courseBaseService.getCourseBaseInfoByPage(searchModel);
        return new BaseResponse<>(courseBaseModelPage);
    }

    @ApiOperation(value = "修改单条课程信息", notes = "修改单条课程信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/modify")
    public BaseResponse<?> modifyCourseBaseInfo(@RequestBody CourseBaseModel courseBaseModel) {
        validateCourseBaseModelParam(courseBaseModel);
        courseBaseService.modifyCourseBaseInfo(courseBaseModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除课程信息", notes = "删除课程信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteCourseInfos")
    public BaseResponse<?> deleteCourseInfos(@RequestBody List<Long> ids) {
        courseBaseService.deleteCourseBaseInfos(ids);
        return new BaseResponse<>();
    }

    /**
     * 校验请求参数
     *
     * @param courseBaseModel
     */
    private void validateCourseBaseModelParam(CourseBaseModel courseBaseModel) {
        if (courseBaseModel.getId() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "课程信息ID为空");
        }
        if (StringUtils.isBlank(courseBaseModel.getCourseName())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "课程名为空");
        }
        if (courseBaseModel.getCourseType() != null && !CourseTypeEnum.isExistByCode(courseBaseModel.getCourseType())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "不存在该课程类型");
        }
        if (courseBaseModel.getCourseCharacter() != null && !CourseCharacterEnum
            .isExistByCode(courseBaseModel.getCourseCharacter())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "不存在该课程性质");
        }
        if (courseBaseModel.getIsBilingual() != null && !IsBilingualEnum
            .isExistByCode(courseBaseModel.getIsBilingual())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "是否双语类型错误");
        }
        if (courseBaseModel.getStudentType() != null && !StudentTypeEnum
            .isExistByCode(courseBaseModel.getStudentType())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "不存在该学生类型");
        }
    }

}