package com.management.common.listener;

import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @description easyExcel文件上传封装类，增加list存储一定数量的记录方便批量存入 </br>
 * 增加解析成功插入数与解析失败记录信息  </br>
 * T返回失败模型层，K为对应的实体类
 **/
public abstract class EasyExcelUploadListener<T, K> extends AnalysisEventListener<T> {

    /**
     * 每隔一定数目存储数据库，然后清理list，方便内存回收
     */
    protected List<K> list = new ArrayList<>();

    /**
     * 数据解析成功记录数
     */
    protected Integer successCount = 0;

    /**
     * 数据解析失败或拒绝插入记录信息
     */
    protected List<T> rejectInsertList = new ArrayList<>();

    public List<K> getList() {
        return list;
    }

    public void setList(List<K> list) {
        this.list = list;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public List<T> getRejectInsertList() {
        return rejectInsertList;
    }

    public void setRejectInsertList(List<T> rejectInsertList) {
        this.rejectInsertList = rejectInsertList;
    }
}
