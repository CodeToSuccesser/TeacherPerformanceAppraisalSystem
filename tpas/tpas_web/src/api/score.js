import request from '@/utils/request'

export function queryScoreList(path, data) {
  return request({
    url: '/score/' + path + '/queryScoreList',
    method: 'post',
    data
  })
}

export function exportScoreFile(path, data) {
  return request({
    url: '/score/' + path + '/export',
    method: 'post',
    responseType: 'blob',
    data
  })
}

export function calculateAssess(data) {
  return request({
    url: '/params/info/calculateAssess',
    method: 'post',
    data
  })
}

export function queryAssessList(data) {
  return request({
    url: '/score/queryAssessList',
    method: 'post',
    data
  })
}

export function assessDelete(data) {
  return request({
    url: '/score/deleteScore',
    method: 'post',
    data
  })
}

export function getScoreAnalysis(data) {
  return request({
    url: '/score/queryScoreAnalysis',
    method: 'post',
    data
  })
}
