import Vue from 'vue'

// loading框设置局部刷新，且所有请求完成后关闭loading框
let loading
let needLoadingRequestCount = 0 // 声明一个对象用于存储请求个数
function startLoading(text) {
  loading = Vue.prototype.$loading({
    lock: true,
    text: text,
    background: 'rgba(255,255,255,.4)'
  })
}

function endLoading() {
  loading.close()
}
export function showFullScreenLoading(text) {
  if (needLoadingRequestCount === 0) {
    startLoading(text)
  }
  needLoadingRequestCount++
}
export function hideFullScreenLoading() {
  if (needLoadingRequestCount <= 0) return
  needLoadingRequestCount--
  if (needLoadingRequestCount === 0) {
    endLoading()
  }
}

export default {
  showFullScreenLoading,
  hideFullScreenLoading
}
