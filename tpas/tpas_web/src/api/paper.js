import request from '@/utils/request'

/**
 * 论文指导信息接口
 */

/**
 * 获取论文指导信息接口
 * @param data
 * @returns {AxiosPromise}
 */
export function getPaperInfo(data) {
  return request({
    url: '/paper/Info/getPaperInfo',
    method: 'post',
    data
  })
}

/**
 * 下载论文导入模板
 * @returns {AxiosPromise}
 */
export function downloadPaperTemplate() {
  return request({
    url: '/paper/Info/download/template',
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 导出论文信息
 * @param data
 * @returns {AxiosPromise}
 */
export function exportPaperInfo(data) {
  return request({
    url: '/paper/Info/export',
    method: 'post',
    responseType: 'blob',
    data
  })
}

/**
 * 导入论文信息
 * @param data
 * @returns {AxiosPromise}
 */
export function importPaperInfo(data) {
  return request({
    url: '/paper/Info/import',
    method: 'post',
    data
  })
}

/**
 * 修改论文记录
 * @param data
 * @param id
 * @returns {AxiosPromise}
 */
export function modifyPaperInfo(data, id) {
  return request({
    url: '/paper/Info/' + id + '/Modify',
    method: 'post',
    data
  })
}

/**
 * 删除论文记录
 * @param data
 * @returns {AxiosPromise}
 */
export function deletePaperInfo(data) {
  return request({
    url: '/paper/Info/deletePaperInfo',
    method: 'post',
    data
  })
}

/**
 * 插入单条
 * @param data
 * @returns {AxiosPromise}
 */
export function insertPaperInfo(data) {
  return request({
    url: '/paper/Info/insert',
    method: 'post',
    data
  })
}

/**
 * 获取论文指导申请记录
 * @param data
 * @returns {AxiosPromise}
 */
export function getPaperModifyRecord(data) {
  return request({
    url: '/paper/Info/getModifyRecord',
    method: 'post',
    data
  })
}

/**
 * 审批论文指导修改申请
 * @param id
 * @param result
 * @returns {AxiosPromise}
 */
export function auditPaperModify(id, result) {
  return request({
    url: '/paper/Info/' + id + '/audit?result=' + result,
    method: 'post'
  })
}
