import request from '@/utils/request'

export function queryCNumIndex() {
  return request({
    url: '/params/info/queryCNumList',
    method: 'post',
    data: {}
  })
}

export function queryParamRules(data) {
  return request({
    url: 'params/info/queryParamRules',
    method: 'post',
    data
  })
}
