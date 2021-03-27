/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * Parse the time to string
 * @param {(Object|string|number)} time
 * @param {string} cFormat
 * @returns {string | null}
 */
export function parseTime(time, cFormat) {
  if (arguments.length === 0 || !time) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if ((typeof time === 'string')) {
      if ((/^[0-9]+$/.test(time))) {
        // support "1548221490638"
        time = parseInt(time)
      } else {
        // support safari
        // https://stackoverflow.com/questions/4310953/invalid-date-in-safari
        time = time.replace(new RegExp(/-/gm), '/')
      }
    }

    if ((typeof time === 'number') && (time.toString().length === 10)) {
      time = time * 1000
    }
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{([ymdhisa])+}/g, (result, key) => {
    const value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value ] }
    return value.toString().padStart(2, '0')
  })
  return time_str
}

/**
 * @param {number} time
 * @param {string} option
 * @returns {string}
 */
export function formatTime(time, option) {
  if (('' + time).length === 10) {
    time = parseInt(time) * 1000
  } else {
    time = +time
  }
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getMonth() +
      1 +
      '月' +
      d.getDate() +
      '日' +
      d.getHours() +
      '时' +
      d.getMinutes() +
      '分'
    )
  }
}

/**
 * @param {string} url
 * @returns {Object}
 */
export function param2Obj(url) {
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ')
  if (!search) {
    return {}
  }
  const obj = {}
  const searchArr = search.split('&')
  searchArr.forEach(v => {
    const index = v.indexOf('=')
    if (index !== -1) {
      const name = v.substring(0, index)
      const _val = v.substring(index + 1, v.length)
      obj[name] = _val
    }
  })
  return obj
}

export function string2List(data) {
  let target = []
  if (data) {
    data.split(',').forEach(it => {
      target.push(it.trim())
    })
  }
  return target
}

/** 分组函数
 * groupBy(list, function(item){
 *   return [item.name];
 * })
 **/
export function groupBy(array, f) {
  const groups = {}
  array.forEach(function(o) {
    const group = JSON.stringify(f(o))
    groups[group] = groups[group] || []
    groups[group].push(o)
  })
  return Object.keys(groups).map(function(group) {
    return groups[group]
  })
}

export function groupToMenuTree(list, allPermission, parentValue) {
  const menuList = []
  const childList = list.filter(menu => menu.parentValue === parentValue)
  const permission = allPermission.filter(permission => permission.value === parentValue)
  childList.forEach(item => {
    const data = {
      id: item.id,
      label: item.label,
      parentValue: item.parentValue,
      level: item.level,
      value: item.value,
      children: groupToMenuTree(list, allPermission, item.value),
      type: 'M'
    }
    menuList.push(data)
  })
  if (permission) {
    permission.forEach(item => {
      const data = {
        id: item.id,
        label: item.permissionName,
        parentValue: item.value,
        value: item.permissionKey,
        type: 'P'
      }
      menuList.push(data)
    })
  }
  return menuList
}

// array 去重
export function unique(list) {
  const res = []
  const json = {}
  for (let i = 0; i < list.length; i++) {
    if (!json[list[i]]) {
      res.push(list[i])
      json[list[i]] = 1
    }
  }
  return res
}

// list 中根据 值移除元素
export function removeObject(list, val) {
  const index = list.indexOf(val)
  if (index > -1) {
    list.splice(index, 1)
  }
  return list
}
