// Excel文件下载
export function downloadExcel(data, fileName) {
  if (typeof window.chrome !== 'undefined') {
    // Chrome version
    var blob = new Blob([data], { type: 'application/vnd.ms-excel' })
    var link = document.createElement('a')
    link.href = window.URL.createObjectURL(blob)
    link.download = fileName
    link.click()
  } else if (typeof window.navigator.msSaveBlob !== 'undefined') {
    // IE version
    blob = new Blob([data], { type: 'application/force-download' })
    window.navigator.msSaveBlob(blob, fileName)
  } else {
    // Firefox version
    var file = new File([data], fileName, { type: 'application/force-download' })
    window.open(URL.createObjectURL(file))
  }
}
