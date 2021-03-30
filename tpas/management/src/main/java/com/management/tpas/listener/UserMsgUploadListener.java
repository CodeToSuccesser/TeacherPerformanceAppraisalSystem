package com.management.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.management.common.constant.Constant;
import com.management.common.listener.EasyExcelUploadListener;
import com.management.common.utils.CommonUtil;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.RegisterMsgModel;
import com.management.tpas.model.SystemRoleModel;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.SystemRoleService;
import com.management.tpas.service.UserMsgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dude
 * @version 1.0
 * @classname UserMsgUploadListener
 * @description 导入用户
 * @date 2021/3/29
 **/
public class UserMsgUploadListener extends EasyExcelUploadListener<RegisterMsgModel, UserMsg> {

    private static final Logger logger = LoggerFactory.getLogger(UserMsgUploadListener.class);

    private UserMsgService userMsgService;

    private List<String> roleNameList;

    public UserMsgUploadListener(UserMsgService userMsgService, SystemRoleService roleService) {
        this.userMsgService = userMsgService;
        this.roleNameList = roleService.getRoles().stream().map(SystemRoleModel::getName).collect(Collectors.toList());
    }

    @Override
    public void invoke(RegisterMsgModel registerMsgModel, AnalysisContext analysisContext) {
        filterRegisterModel(registerMsgModel);

        if(list.size() >= Constant.EASYEXCEL_UPLOAD_BATCH_SIZE) {
            userMsgService.insertBatchUserMsg(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        userMsgService.insertBatchUserMsg(list);
        logger.info("successfully upload user info file");
    }

    /**
     * 解析校验注册信息，转换成用户数据
     *
     * @author dude
     * @date 2021/3/29
     **/
    private void filterRegisterModel(RegisterMsgModel model) {
        if (!StringUtils.isBlank(model.getRegisterName())
                && !StringUtils.isBlank(model.getLogName())) {
            UserMsgModel msg = userMsgService.getByLoginName(model.getLogName());
            if (msg == null) {
                List<String> userRole;
                if (StringUtils.isBlank(model.getRolesName())) {
                    userRole = Collections.emptyList();
                } else {
                    userRole = CommonUtil.parseStringList(model.getRolesName(), ",");
                }
                List<String> exitRole = userRole.stream().filter(roleNameList::contains).collect(Collectors.toList());
                UserMsg userMsg = new UserMsg();
                userMsg.setUserName(model.getRegisterName());
                userMsg.setLogName(model.getLogName());
                userMsg.setRolesName(StringUtils.join(exitRole.iterator(), ","));
                userMsg.setContact(model.getContact());
                list.add(userMsg);
                ++successCount;
                return;
            }
        }
        rejectInsertList.add(model);
    }
}
