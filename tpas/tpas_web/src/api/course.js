import request from '@/utils/request'

export function getCourseBaseInfo(params) {
  return request({
    url: 'course/info/getCourseInfo',
    method: 'get',
    params: { params }
  })
}
