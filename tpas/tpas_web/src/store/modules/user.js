import { login, getInfo, modifyUserInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    account: '',
    portrait: '',
    contact: '',
    rolesName: [],
    routerMenus: []
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
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, type } = userInfo
    return new Promise((resolve, reject) => {
      login({ logName: username.trim(), logPassword: password, userType: type }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        commit('SET_NAME', data.userName)
        commit('SET_PORTRAIT', data.portrait)
        commit('SET_ACCOUNT', data.logName)
        commit('SET_CONTACT', data.contact)
        commit('SET_ROLES_NAME', data.rolesName)
        commit('SET_ROUTER_MENUS', data.routerMenus)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
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

