import request from '@/utils/request'

/**
 * 专业信息接口
 */

/**
 * 获取专业信息
 * @param data
 * @returns {AxiosPromise}
 */
export function getMajorInfo(data) {
  return request({
    url: '/major/info/getMajor',
    method: 'post',
    data
  })
}

/**
 * 插入专业信息
 * @param data
 * @returns {AxiosPromise}
 */
export function insertMajor(data) {
  return request({
    url: '/major/info/insert',
    method: 'post',
    data
  })
}

/**
 *  修改专业信息
 * @param data
 * @param id
 * @returns {AxiosPromise}
 */
export function modifyMajor(data, id) {
  return request({
    url: '/major/info/' + id + '/modify',
    method: 'post',
    data
  })
}

/**
 * 删除专业信息
 * @param data
 * @returns {AxiosPromise}
 */
export function deleteMajor(data) {
  return request({
    url: '/major/info/deleteMajor',
    method: 'post',
    data
  })
}

/**
 * 导出专业信息
 * @param data
 * @returns {AxiosPromise}
 */
export function exportMajor(data) {
  return request({
    url: '/major/info/export',
    method: 'post',
    responseType: 'blob',
    data
  })
}

/**
 * 模板文件下载
 * @param data
 * @returns {AxiosPromise}
 */
export function downloadTemplate() {
  return request({
    url: '/major/info/download/template',
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 导入专业信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function importMajorFile(data) {
  return request({
    url: '/major/info/import',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>'
    },
    data
  })
}

