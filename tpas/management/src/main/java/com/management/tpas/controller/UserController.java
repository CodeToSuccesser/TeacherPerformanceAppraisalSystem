package com.management.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.pagehelper.PageInfo;
import com.management.common.config.FileConfig;
import com.management.common.constant.Constant;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.common.model.UploadResponseModel;
import com.management.common.utils.FileUtil;
import com.management.tpas.listener.UserMsgUploadListener;
import com.management.tpas.model.*;
import com.management.tpas.service.SystemRoleService;
import com.management.tpas.service.UserMsgService;
import com.management.tpas.utils.UserUtil;
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
 * @author dude
 * @version 1.0
 * @classname UserController
 * @description 用户登录接口
 * @date 2020/8/9
 **/
@Api(tags = {"用户登录权限相关接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private SystemRoleService roleService;

    private static final String fileName = "用户管理模板文件.xlsx";


    @Autowired
    private FileConfig fileConfig;

    /**
     * @param loginMsgModel 登录信息
     * @return java.lang.String
     * @description 登录处理接口
     * @author dude
     * @date 2020/8/9
     **/
    @PostMapping("/login")
    @ApiOperation("登录处理接口")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = UserMsgModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> login(@RequestBody LoginMsgModel loginMsgModel) {
        if (null == loginMsgModel
                || StringUtils.isBlank(loginMsgModel.getLogName())
                || StringUtils.isBlank(loginMsgModel.getLogPassword())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY.code, ErrorCodeEnum.PARAM_IS_EMPTY.msg);
        }
        // 获取账号信息,生成jwt和设置缓存
        return new BaseResponse<>(userMsgService.getByLoginMsg(loginMsgModel));
    }

    /**
     * @param model: 用户姓名、登录名、密码、用户类型
     * @Description 新增/编辑一条用户
     * @Return BaseResponse<?>
     * @Author peihua.wu
     * @Date 2020/8/16
     **/
    @PostMapping("/editUserInfo")
    @ApiOperation("新增/编辑一条用户")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = UserMsgModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> editUserInfo(@RequestBody UserMsgModel model) {
        if (model == null || StringUtils.isBlank(model.getLogName()) || StringUtils.isBlank(model.getUserName())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        // 插入新数据
        userMsgService.editUserInfo(model);
        return new BaseResponse<>();
    }

    @PostMapping("/modifyUserInfo")
    @ApiOperation(value = "编辑用户信息", notes = "个人中心信息编辑")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = UserMsgModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> modifyUserInfo(MultipartFile file, RegisterMsgModel model) {
        // 无新修改字段
        if (StringUtils.isBlank(model.getContact()) && StringUtils.isBlank(model.getPassword()) && file == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        // 判断注册用户类型
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (null == userMsgModel) {
            throw new BusinessException(ErrorCodeEnum.LOGIN_TIME_OUT);
        } else {
            model.setType(userMsgModel.getUserType());
            model.setLogName(userMsgModel.getLogName());
        }
        return new BaseResponse<>(userMsgService.updateUserMsg(model, file));
    }

    @PostMapping("/queryUserInfo")
    @ApiOperation(value = "查询用户列表", notes = "用户管理列表")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = UserMsgModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> queryUserInfo(@RequestBody UserInfoSearchModel searchModel) {
        if (searchModel == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        PageInfo<UserMsgModel> data = userMsgService.queryUserInfo(searchModel);
        return new BaseResponse<>(data);
    }

    /**
     * 删除用户
     *
     * @author dude
     * @date 2021/3/29
     **/
    @PostMapping("/deleteUser")
    @ApiOperation("删除用户")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> deleteUser(@RequestBody List<String> logNameList) {
        if (logNameList == null || logNameList.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        return new BaseResponse<>(userMsgService.deleteUser(logNameList));
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重设密码，重设为123456")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> resetPassword(@RequestBody List<String> logNameList) {
        if (logNameList == null || logNameList.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        return new BaseResponse<>(userMsgService.resetUserPassword(logNameList));
    }

    @PostMapping("/import")
    @ApiOperation("导入用户信息文件")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> uploadUserInfo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("upload file is empty");
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "上传文件为空");
        }
        final UserMsgUploadListener userMsgUploadListener =
                new UserMsgUploadListener(userMsgService, roleService);
        try {
            EasyExcel.read(file.getInputStream(), RegisterMsgModel.class, userMsgUploadListener).sheet().doRead();
        } catch (IOException e) {
            logger.error("fail to upload course hours file");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }

        Integer successCount = userMsgUploadListener.getSuccessCount();
        List<RegisterMsgModel> rejectList = userMsgUploadListener.getRejectInsertList();
        Integer failCount = rejectList.size();

        return new BaseResponse<>(new UploadResponseModel<>(rejectList, successCount, failCount,
                successCount + failCount));
    }

    @ApiOperation(value = "下载用户信息模板", notes = "下载用户信息模板")
    @GetMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, Constant.EASYEXCEL_ENCODING));
            FileUtil.downloadFileByPath(response, fileConfig.SampleFileMenu + "/" + fileName,
                    Constant.EASYEXCEL_CONTENT_TYPE, headerMap);
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }
    }

    @ApiOperation(value = "导出用户信息文件", notes = "导出用户信息文件")
    @PostMapping("/export")
    public void exportCourseInfo(HttpServletResponse response, @RequestBody UserInfoSearchModel searchModel)
            throws IOException {
        List<UserMsgModel> userModelList = userMsgService.getUserModelList(searchModel);

        response.setContentType(Constant.EASYEXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.EASYEXCEL_ENCODING);
        String fileName = URLEncoder.encode("用户信息导出文件", Constant.EASYEXCEL_ENCODING);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserMsgModel.class).sheet("课时信息")
                // 设置字段宽度为自动调整
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(userModelList);
    }
}
