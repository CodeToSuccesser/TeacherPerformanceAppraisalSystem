import request from '@/utils/request'

/**
 * 课时接口
 */

/**
 * 获取课时基本信息
 * @param data
 * @returns {AxiosPromise}
 */
export function getCourseHours(data) {
  return request({
    url: '/course/hours/getCourseHours',
    method: 'post',
    data
  })
}

/**
 * 下载课时导入模板
 * @returns {AxiosPromise}
 */
export function downCourseHoursTemplate() {
  return request({
    url: '/course/hours/download/template',
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 导出课时信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function exportCourseFile(data) {
  return request({
    url: '/course/hours/export',
    method: 'post',
    responseType: 'blob',
    data
  })
}

/**
 * 导入课时信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function importCourseHoursFile(data) {
  return request({
    url: '/course/hours/import',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>'
    },
    data
  })
}

/**
 * 修改课时记录
 * @param data
 * @param id
 * @returns {AxiosPromise}
 */
export function modifyCourseHours(data, id) {
  return request({
    url: '/course/hours/' + id + '/modify',
    method: 'post',
    data
  })
}

/**
 * 删除课时信息
 * @param data
 * @returns {AxiosPromise}
 */
export function deleteCourseHours(data) {
  return request({
    url: '/course/hours/deleteCourseHours',
    method: 'post',
    data
  })
}

/**
 * 插入单条课时记录
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

/**
 * 获取课时记录修改申请
 * @param data
 * @returns {AxiosPromise}
 */
export function getCourseHoursModifyRecord(data) {
  return request({
    url: '/course/hours/getModifyRecord',
    method: 'post',
    data
  })
}

/**
 * 审批课时修改记录
 * @param id
 * @param result
 * @returns {AxiosPromise}
 */
export function auditCourseHoursModify(id, result) {
  return request({
    url: '/course/hours/' + id + '/audit?result=' + result,
    method: 'post'
  })
}

/** course base api **/

/**
 * 查询课程信息
 * @param data
 * @returns {AxiosPromise}
 */
export function queryCourseBase(data) {
  return request({
    url: '/course/info/getCourseInfo',
    method: 'post',
    data
  })
}

/**
 * 下载课程管理文件模板
 * @param data
 * @returns {AxiosPromise}
 */
export function downloadCourseInfoTemplate() {
  return request({
    url: '/course/info/download/template',
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 导入课程信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function uploadCourseInfo(data) {
  return request({
    url: '/course/info/upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>'
    },
    data
  })
}

/**
 * 导出课程信息文件
 * @param data
 * @returns {AxiosPromise}
 */
export function exportCourseInfo(data) {
  return request({
    url: '/course/info/export',
    method: 'post',
    responseType: 'blob',
    data
  })
}

/**
 * 修改单条课程信息
 * @param data
 * @returns {AxiosPromise}
 */
export function editCourseBaseInfo(data, id) {
  return request({
    url: '/course/info/' + id + '/modify',
    method: 'post',
    data
  })
}

/**
 * 插入单条课程信息记录
 * @param data
 * @returns {AxiosPromise}
 */
export function addCourseBaseInfo(data) {
  return request({
    url: '/course/info/insert',
    method: 'post',
    data
  })
}

/**
 * 删除课程信息
 * @param data
 * @returns {AxiosPromise}
 */
export function deleteCourseInfos(data) {
  return request({
    url: '/course/info/deleteCourseInfos',
    method: 'post',
    data
  })
}
