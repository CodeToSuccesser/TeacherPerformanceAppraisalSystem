package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.business.tpas.constant.Constant;
import com.business.tpas.enums.SemesterEnum;
import com.business.tpas.listener.InternUploadListener;
import com.business.tpas.model.InternModel;
import com.business.tpas.model.InternSearchModel;
import com.business.tpas.model.UploadResponseModel;
import com.business.tpas.service.InternService;
import com.business.tpas.utils.FileUtil;
import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.tpas.service.TeacherMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 实习带队信息相关接口
 **/

@RestController
@RequestMapping("/intern/info")
@Api(tags = {"实习带队信息相关接口"})
public class InternInfoController {

    private static final Logger logger = LoggerFactory.getLogger(InternInfoController.class);

    private static final String filePath = "template/实习带队信息模板.xls";

    private static final String fileName = "实习带队信息模板.xls";

    @Autowired
    private InternService internService;

    @Autowired
    private TeacherMsgService teacherMsgService;

    /**
     * 下载实习带队信息模板文件
     * @param response
     */
    @ApiOperation(value = "下载实习带队信息模板文件", notes = "下载实习带队信息模板文件")
    @GetMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, Constant.EASYEXCEL_ENCODING));
            FileUtil.downloadClassPathFile(response, filePath, Constant.EASYEXCEL_CONTENT_TYPE, headerMap);
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }
    }

    @ApiOperation(value = "导入实习带队信息文件", notes = "导入实习带队信息文件")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/import")
    public BaseResponse<?> uploadInterInfo(@RequestParam("file") MultipartFile file,
        @RequestParam("semester") Integer semester, @RequestParam("schoolYear") String schoolYear) {
        if (file.isEmpty()) {
            logger.error("upload file is empty");
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "上传文件为空");
        }
        // 校验学期和学年参数
        validateImportParam(schoolYear, semester);

        final InternUploadListener internUploadListener =
            new InternUploadListener(internService, teacherMsgService, schoolYear, semester);

        try {
            EasyExcel.read(file.getInputStream(), InternModel.class, internUploadListener).sheet().doRead();
        } catch (IOException e) {
            logger.error("fail to upload intern info file");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }

        Integer successCount = internUploadListener.getSuccessCount();
        List<InternModel> internModels = internUploadListener.getRejectInsertList();
        Integer failCount = internModels.size();

        return new BaseResponse<>(
            new UploadResponseModel<>(internModels, successCount, failCount, successCount + failCount));
    }

    @ApiOperation(value = "导出实习带队信息文件", notes = "导出实习带队信息文件")
    @GetMapping("/export")
    public void exportInternInfo(HttpServletResponse response, @RequestBody InternSearchModel searchModel)
        throws IOException {
        List<InternModel> internModels = internService.getInternInfo(searchModel);

        response.setContentType(Constant.EASYEXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.EASYEXCEL_ENCODING);
        String fileName = URLEncoder.encode("实习带队信息导出", Constant.EASYEXCEL_ENCODING);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), InternModel.class).sheet("实习带队信息")
            // 设置字段宽度为自动调整
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(internModels);
    }

    @ApiOperation(value = "根据条件查询实习带队信息", notes = "根据条件查询实习带队信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = InternModel .class),
        @ApiResponse(code = 500, message = "系统错误")})
    @GetMapping("/getInternInfo")
    public BaseResponse<?> getInternInfo(@RequestBody InternSearchModel searchModel) {
        PageInfo<InternModel> internModelPageInfo = internService.getInternInfoByPage(searchModel);
        return new BaseResponse<>(internModelPageInfo);
    }

    @ApiOperation(value = "修改单条实习带队信息", notes = "修改单条实习带队信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyInternInfo(@PathVariable("id") Long id, @RequestBody InternModel internModel) {
        validateModifyInsertInternModelParam(internModel);
        internModel.setId(id);
        internService.modifyInternInfo(internModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除实习带队信息", notes = "删除实习带队信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteInternInfo")
    public BaseResponse<?> deleteInternInfo(@RequestBody List<Long> ids) {
        internService.deleteInternInfos(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "插入单条实习带队信息记录" ,notes = "插入单条实习带队信息记录")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertInternInfo(@RequestBody InternModel internModel) {
        validateModifyInsertInternModelParam(internModel);
        internService.insertInternModel(internModel);
        return new BaseResponse<>();
    }

    /**
     * 校验文件导入的学期和学年参数
     * @param schoolYear
     * @param semester
     */
    private void validateImportParam(String schoolYear, Integer semester) {
        if (StringUtils.isBlank(schoolYear)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学年参数为空");
        }
        if (semester == null || !SemesterEnum.isExistByCode(semester)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "学期参数不合理");
        }
    }

    /**
     * 校验修改或新增参数
     * @param internModel
     */
    private void validateModifyInsertInternModelParam(InternModel internModel) {
        if (StringUtils.isBlank(internModel.getTeacherCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "教师编码参数为空");
        }
        validateImportParam(internModel.getSchoolYear(), internModel.getSemester());
    }

}
