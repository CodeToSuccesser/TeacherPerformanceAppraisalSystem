package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.business.tpas.constant.Constant;
import com.business.tpas.enums.SemesterEnum;
import com.business.tpas.listener.PaperUploadListener;
import com.business.tpas.model.*;
import com.business.tpas.service.MajorService;
import com.business.tpas.service.PaperModifyRecordService;
import com.business.tpas.service.PaperService;
import com.business.tpas.utils.FileUtil;
import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.tpas.service.UserMsgService;
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
 * @description 论文指导管理相关接口
 **/

@RestController
@RequestMapping("/paper/info")
@Api(tags = {"论文指导管理相关接口"})
public class PaperInfoController {

    private static final Logger logger = LoggerFactory.getLogger(PaperInfoController.class);

    private static final String filePath = "template/论文指导信息模板.xls";

    private static final String fileName = "论文指导信息模板.xls";

    @Autowired
    private MajorService majorService;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private PaperModifyRecordService paperModifyRecordService;

    /**
     * 下载论文指导信息模板
     * @param response
     */
    @ApiOperation(value = "下载论文指导信息模板", notes = "下载论文指导信息模板")
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

    @ApiOperation(value = "导入论文指导信息文件", notes = "导入论文指导信息文件")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/import") public BaseResponse<?> uploadPaperInfo(@RequestParam("file") MultipartFile file,
        @RequestParam("semester") Integer semester, @RequestParam("schoolYear") String schoolYear) {
        if (file.isEmpty()) {
            logger.error("upload file is empty");
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "上传文件为空");
        }
        // 校验学期和学年参数
        validateImportParam(schoolYear, semester);

        final PaperUploadListener paperUploadListener =
            new PaperUploadListener(majorService, userMsgService, paperService, schoolYear, semester);

        try {
            EasyExcel.read(file.getInputStream(), PaperModel.class, paperUploadListener).sheet().doRead();
        } catch (IOException e) {
            logger.error("fail to upload paper info file");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }

        Integer successCount = paperUploadListener.getSuccessCount();
        List<PaperModel> paperModels = paperUploadListener.getRejectInsertList();
        Integer failCount = paperModels.size();

        return new BaseResponse<>(
            new UploadResponseModel<>(paperModels, successCount, failCount, successCount + failCount));
    }

    @ApiOperation(value = "导出论文指导信息文件", notes = "导出论文指导信息文件")
    @GetMapping("/export")
    public void exportPaperInfo(HttpServletResponse response, @RequestBody PaperSearchModel searchModel)
        throws IOException {
        List<PaperModel> paperModels = paperService.getPaperInfo(searchModel);

        response.setContentType(Constant.EASYEXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.EASYEXCEL_ENCODING);
        String fileName = URLEncoder.encode("论文指导信息导出", Constant.EASYEXCEL_ENCODING);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), PaperModel.class).sheet("论文指导信息")
            // 设置字段宽度为自动调整
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(paperModels);
    }

    @ApiOperation(value = "根据条件查询论文指导信息", notes = "根据条件查询论文指导信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = PaperModel .class),
        @ApiResponse(code = 500, message = "系统错误")})
    @GetMapping("/getPaperInfo")
    public BaseResponse<?> getPaperInfo(@RequestBody PaperSearchModel searchModel) {
        PageInfo<PaperModel> paperModelPageInfo = paperService.getPaperInfoByPage(searchModel);
        return new BaseResponse<>(paperModelPageInfo);
    }

    @ApiOperation(value = "修改单条论文指导信息", notes = "修改单条论文指导信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyPaperInfo(@PathVariable("id") Long id, @RequestBody PaperModel paperModel) {
        validateModifyInsertPaperModelParam(paperModel);
        paperModel.setId(id);
        paperService.modifyPaperInfo(paperModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除论文指导信息", notes = "删除论文指导信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deletePaperInfo")
    public BaseResponse<?> deletePaperInfo(@RequestBody List<Long> ids) {
        paperService.deletePaperInfos(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "插入单条论文指导信息记录" ,notes = "插入单条论文指导信息记录")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertPaperInfo(@RequestBody PaperModel paperModel) {
        validateModifyInsertPaperModelParam(paperModel);
        paperService.insertPaperInfo(paperModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "查找论文指导修改信息记录", notes = "查找论文指导修改信息记录")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = PaperModifyRecordModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    @GetMapping("/getModifyRecord")
    public BaseResponse<?> getModifyRecord(@RequestBody PaperModifyRecordSearchModel searchModel) {
        return new BaseResponse<>(paperModifyRecordService.getModifyRecord(searchModel));
    }

    @ApiOperation(value = "论文指导修改审批",notes = "论文指导修改审批")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/{id}/audit")
    public BaseResponse<?> auditPaperModify(@PathVariable Long id, @RequestParam("result") Boolean result) {
        if (result == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "审批结果参数为空");
        }
        paperModifyRecordService.auditPaperModify(id, result);
        return new BaseResponse<>();
    }

    private void validateModifyInsertPaperModelParam(PaperModel paperModel) {
        if (StringUtils.isBlank(paperModel.getMajorCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "专业编码参数为空");
        }
        if (StringUtils.isBlank(paperModel.getTeacherCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "教师编码参数为空");
        }
        if (paperModel.getStudentNumber() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "指导学生人数为空");
        }
        if (StringUtils.isBlank(paperModel.getSchoolYear())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学年参数为空");
        }
        if (paperModel.getSemester() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学期参数为空");
        }
    }

    private void validateImportParam(String schoolYear, Integer semester) {
        if (StringUtils.isBlank(schoolYear)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学年参数为空");
        }
        if (semester == null || !SemesterEnum.isExistByCode(semester)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "学期参数不合理");
        }
    }

}
