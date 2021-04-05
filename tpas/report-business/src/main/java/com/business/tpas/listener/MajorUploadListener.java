package com.business.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.business.tpas.entity.Major;
import com.business.tpas.model.MajorModel;
import com.business.tpas.service.MajorService;
import com.management.common.constant.Constant;
import com.management.common.listener.EasyExcelUploadListener;
import com.management.common.utils.BeanMapper;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 导入专业信息文件读取类
 **/
public class MajorUploadListener extends EasyExcelUploadListener<MajorModel, Major> {

    private static final Logger logger = LoggerFactory.getLogger(MajorUploadListener.class);

    private MajorService majorService;

    public MajorUploadListener(MajorService majorService) {
        this.majorService = majorService;
    }

    @Override
    public void invoke(MajorModel data, AnalysisContext context) {
        filterMajorModel(data);

        // 达到BATCH_COUNT，存储一次数据库，防止OOM
        if (list.size() >= Constant.EASYEXCEL_UPLOAD_BATCH_SIZE) {
            majorService.insertBatchMajor(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        majorService.insertBatchMajor(list);
        logger.info("successfully upload major file");
    }

    /**
     * 解析转换单条专业记录，并根据数据格式合法与否选择插入或是忽略
     *
     * @param data
     */
    private void filterMajorModel(MajorModel data) {
        if (StringUtils.isBlank(data.getMajorCode()) || StringUtils.isBlank(data.getMajorName())) {
            rejectInsertList.add(data);
        }
        if (majorService.selectByMajorCode(data.getMajorCode()) != null) {
            rejectInsertList.add(data);
        } else {
            data.setAdminId(UserUtil.getUserId());
            list.add(BeanMapper.map(data, Major.class));
            ++successCount;
        }
    }

}
