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

