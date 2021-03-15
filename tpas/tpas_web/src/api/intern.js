import request from '@/utils/request'

/**
 * 实习带队接口
 */

/**
 * 获取实习带队基本信息
 * @param data
 * @returns {AxiosPromise}
 */
export function getInternInfo(data) {
  return request({
    url: '/intern/Info/getInternInfo',
    method: 'post',
    data
  })
}

/**
 * 下载实习带队模板
 * @returns {AxiosPromise}
 */
export function downInternInfoTemplate() {
  return request({
    url: '/intern/Info/download/template',
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 导出实习带队信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function exportInternFile(data) {
  return request({
    url: '/intern/Info/export',
    method: 'post',
    responseType: 'blob',
    data
  })
}

/**
 * 导入实习带队信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function importInternInfoFile(data) {
  return request({
    url: '/intern/Info/import',
    method: 'post',
    data
  })
}

/**
 * 修改实习带队记录
 * @param data
 * @param id
 * @returns {AxiosPromise}
 */
export function modifyInternInfo(data, id) {
  return request({
    url: '/intern/Info/' + id + '/Modify',
    method: 'post',
    data
  })
}

/**
 * 删除实习带队信息
 * @param data
 * @returns {AxiosPromise}
 */
export function deleteInternInfos(data) {
  return request({
    url: '/intern/Info/deleteInternInfo',
    method: 'post',
    data
  })
}

/**
 * 插入单条实习带队信息
 * @param data
 * @returns {AxiosPromise}
 */
export function insertInternInfo(data) {
  return request({
    url: '/intern/Info/insert',
    method: 'post',
    data
  })
}

/**
 * 实习带队修改申请
 * @param data
 * @returns {AxiosPromise}
 */
export function getInternModifyRecord(data) {
  return request({
    url: '/intern/Info/getModifyRecord',
    method: 'post',
    data
  })
}

/**
 * 审批实习带队修改申请
 * @param id
 * @param result
 * @returns {AxiosPromise}
 */
export function auditInternModify(id, result) {
  return request({
    url: '/intern/Info/' + id + '/audit?result=' + result,
    method: 'post'
  })
}
