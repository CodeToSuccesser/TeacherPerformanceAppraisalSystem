import request from '@/utils/request'

/**
 * 其他教务工作信息接口
 */

/**
 * 获取其他教务工作信息
 * @param data
 * @returns {AxiosPromise}
 */
export function getOtherPerformance(data) {
  return request({
    url: '/other/getOtherPerformance',
    method: 'post',
    data
  })
}

/**
 * 插入其他教务工作信息
 * @param data
 * @returns {AxiosPromise}
 */
export function insertOtherPerformance(data) {
  return request({
    url: '/other/insert',
    method: 'post',
    data
  })
}

/**
 * 修改其他教务工作信息
 * @param data
 * @param id
 * @returns {AxiosPromise}
 */
export function modifyOtherPerformance(data, id) {
  return request({
    url: '/other/' + id + '/modify',
    method: 'post',
    data
  })
}

/**
 * 删除其他教务工作信息
 * @param data
 * @param id
 * @returns {AxiosPromise}
 */
export function deleteOtherPerformance(data) {
  return request({
    url: '/other/deleteOtherPerformance',
    method: 'post',
    data
  })
}
