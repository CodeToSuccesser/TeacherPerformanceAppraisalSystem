import request from '@/utils/request'

/**
 * 任务相关接口
 */

/**
 * 获取任务信息接口
 * @param data
 * @returns {AxiosPromise}
 */
export function getTask(data) {
  return request({
    url: '/task/getTask',
    method: 'post',
    data
  })
}

/**
 * 插入任务信息接口
 * @param data
 * @returns {AxiosPromise}
 */
export function insertTask(data) {
  return request({
    url: '/task/insert',
    method: 'post',
    data
  })
}

/**
 * 修改任务信息接口
 * @param data
 * @returns {AxiosPromise}
 */
export function modifyTask(data,id) {
  return request({
    url: '/task/' + id + '/modify',
    method: 'post',
    data
  })
}

/**
 * 删除任务信息接口
 * @param data
 * @returns {AxiosPromise}
 */
export function deleteTask(data) {
  return request({
    url: '/task/deleteTask',
    method: 'post',
    data
  })
}

/**
 * 任务反馈接口
 * @param data
 * @returns {AxiosPromise}
 */
export function taskFeedback(data,id) {
  return request({
    url: '/task/' + id + '/feedBack',
    method: 'post',
    data
  })
}





