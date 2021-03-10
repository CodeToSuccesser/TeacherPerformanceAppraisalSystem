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
    url: '/paper/info/getPaperInfo',
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
    url: '/paper/info/download/template',
    method: 'get'
  })
}

/**
 * 导出论文信息
 * @param data
 * @returns {AxiosPromise}
 */
export function exportPaperInfo(data) {
  return request({
    url: '/paper/info/export',
    method: 'post',
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
    url: '/paper/info/import',
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
    url: '/paper/info/' + id + '/modify',
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
    url: '/paper/info/deletePaperInfo',
    method: 'post',
    data
  })
}

/**
 * 插入单条
 * @param data
 * @returns {AxiosPromise}
 */
export function insertCourseHours(data) {
  return request({
    url: '/course/hours/insert',
    method: 'post',
    data
  })
}

