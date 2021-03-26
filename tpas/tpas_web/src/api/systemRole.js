import request from '@/utils/request'

export function querySystemRoles(data) {
  return request({
    url: 'permission/queryRoles',
    method: 'post',
    data
  })
}

export function querySystemMenus() {
  return request({
    url: 'permission/queryMenus',
    method: 'post',
    data: {}
  })
}

export function editRole(data) {
  return request({
    url: 'permission/editRole',
    method: 'post',
    data
  })
}

export function deleteRole(data) {
  return request({
    url: 'permission/deleteRole',
    method: 'post',
    data
  })
}
