import request from '@/utils/request'

export function queryCNumIndex() {
  return request({
    url: '/params/info/queryCNumList',
    method: 'post',
    data: {}
  })
}

/**
 * 权值元素列表
 * @param data
 * @returns {AxiosPromise}
 */
export function queryParamRules(data) {
  return request({
    url: 'params/info/queryParamRules',
    method: 'post',
    data
  })
}

/**
 * 规则列表
 * @param data
 * @returns {AxiosPromise}
 */
export function queryRuleList(data) {
  return request({
    url: 'params/info/queryRuleList',
    method: 'post',
    data
  })
}

/**
 * 考核规则列表
 * @param data
 * @returns {AxiosPromise}
 */
export function queryAssessList(data) {
  return request({
    url: 'params/info/queryAssessList',
    method: 'post',
    data
  })
}

/**
 * 全部规则
 * @returns {AxiosPromise}
 */
export function getRuleList() {
  return request({
    url: 'params/info/getRuleList',
    method: 'post',
    data: {}
  })
}

export function editParamRules(data) {
  return request({
    url: 'params/info/editParamRules',
    method: 'post',
    data
  })
}

export function editRuleSetting(data) {
  return request({
    url: 'params/info/editRuleSetting',
    method: 'post',
    data
  })
}

export function editAssessRule(data) {
  return request({
    url: 'params/info/editAssessRule',
    method: 'post',
    data
  })
}

export function deleteParamRules(data) {
  return request({
    url: 'params/info/deleteParamRules',
    method: 'post',
    data
  })
}

export function deleteRuleSetting(data) {
  return request({
    url: 'params/info/deleteRuleSetting',
    method: 'post',
    data
  })
}

export function deleteAssessRule(data) {
  return request({
    url: 'params/info/deleteAssessRule',
    method: 'post',
    data
  })
}
