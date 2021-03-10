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
    url: '/intern/info/getInternInfo',
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
    url: '/intern/info/download/template',
    method: 'get'
  })
}

/**
 * 导出实习带队信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function exportInternFile(data) {
  return request({
    url: '/intern/info/export',
    method: 'post',
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
    url: '/intern/info/import',
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
    url: '/intern/info/' + id + '/modify',
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
    url: '/intern/info/deleteInternInfo',
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
    url: '/intern/info/insert',
    method: 'post',
    data
  })
}

