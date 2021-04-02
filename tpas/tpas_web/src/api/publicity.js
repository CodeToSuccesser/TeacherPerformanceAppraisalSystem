import request from '@/utils/request'

export function getPublicity(data) {
  return request({
    url: 'publicity/getPublicity',
    method: 'post',
    data
  })
}

export function deletePublicity(data) {
  return request({
    url: 'publicity/deletePublicity',
    method: 'post',
    data
  })
}

export function insertPublicity(data) {
  return request({
    url: 'publicity/insert',
    method: 'post',
    data
  })
}

