import { login, getInfo, modifyUserInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    id: undefined,
    name: '',
    account: '',
    portrait: '',
    contact: '',
    rolesName: [],
    routerMenus: [],
    permissionMap: {}
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_PORTRAIT: (state, portrait) => {
    state.portrait = portrait
  },
  SET_ACCOUNT: (state, account) => {
    state.account = account
  },
  SET_CONTACT: (state, contact) => {
    state.contact = contact
  },
  SET_ROLES_NAME: (state, rolesName) => {
    state.rolesName = rolesName
  },
  SET_ROUTER_MENUS: (state, routerMenus) => {
    state.routerMenus = routerMenus
  },
  SET_ID: (state, id) => {
    state.id = id
  },
  SET_PERMISSION_MAP: (state, list) => {
    const allPermission = {}
    // 遍历系统下的权限并进行分层
    for (let j = 0; j < list.length; j++) {
      const item = list[j]
      const menu = item.value
      // 初始化权限表
      if (allPermission[menu] === undefined) {
        allPermission[menu] = {}
      }
      const key = item.permissionKey
      const type = item.controlType
      if (type === 1 || type === 3) {
        // fieldName 为空字符串, 是显示的权限
        allPermission[menu][key] = true
      } else if (type === 2 || type === 4) {
        // 有 fieldName, 是搜索的权限
        // 判断是否是对象, 因为有多个权限得用对象存储
        if (allPermission[menu][key] === undefined) {
          allPermission[menu][key] = {}
        }
        const name = item.filedName
        allPermission[menu][key][name] = true
      }
    }
    state.permissionMap = allPermission
  }
}

const actions = {
  // user Login
  login({ commit }, userInfo) {
    const { username, password, type } = userInfo
    return new Promise((resolve, reject) => {
      login({ logName: username.trim(), logPassword: password, userType: type }).then(response => {
        const { data } = response
        commit('SET_ID', data.id)
        commit('SET_TOKEN', data.token)
        commit('SET_NAME', data.userName)
        commit('SET_PORTRAIT', data.portrait)
        commit('SET_ACCOUNT', data.logName)
        commit('SET_CONTACT', data.contact)
        commit('SET_ROLES_NAME', data.rolesName)
        commit('SET_ROUTER_MENUS', data.routerMenus)
        commit('SET_PERMISSION_MAP', data.permissionList)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user Info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }

        const { name, portrait } = data

        commit('SET_NAME', name)
        commit('SET_PORTRAIT', portrait)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      removeToken()
      resetRouter()
      commit('RESET_STATE')
      sessionStorage.clear()
      resolve()
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },

  // modify info
  modifyUserInfo({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      modifyUserInfo(userInfo).then(response => {
        const { data } = response
        commit('SET_NAME', data.userName)
        commit('SET_PORTRAIT', data.portrait.substring(1))
        commit('SET_CONTACT', data.contact)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

