import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/vue-admin-template/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}

export function modifyUserInfo(data) {
  return request({
    url: '/user/modifyUserInfo',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data: data
  })
}

/**
 * 获取用户列表
 */
export function queryUserInfo(data) {
  return request({
    url: '/user/queryUserInfo',
    method: 'post',
    data
  })
}

/**
 * 删除用户
 */
export function deleteUser(data) {
  return request({
    url: '/user/deleteUser',
    method: 'post',
    data
  })
}

/**
 * 新增/编辑用户
 */
export function editUserInfo(data) {
  return request({
    url: '/user/editUserInfo',
    method: 'post',
    data
  })
}

/**
 * 重置密码
 */
export function resetPassword(data) {
  return request({
    url: '/user/resetPassword',
    method: 'post',
    data
  })
}

/**
 * 导入用户文件
 */
export function importUserInfo(data) {
  return request({
    url: '/user/import',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>'
    },
    data
  })
}

/**
 * 下载课时导入模板
 */
export function downloadUserInfoTemplate() {
  return request({
    url: '/user/download/template',
    method: 'get',
    responseType: 'blob'
  })
}

/**
 * 导出用户信息文件
 */
export function exportUserFile(data) {
  return request({
    url: '/user/export',
    method: 'post',
    responseType: 'blob',
    data
  })
}
