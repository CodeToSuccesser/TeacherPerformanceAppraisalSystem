import { login, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    account: '',
    avatar: '',
    contact: '',
    userType: '',
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
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ACCOUNT: (state, account) => {
    state.account = account
  },
  SET_CONTACT: (state, contact) => {
    state.contact = contact
  },
  SET_USER_TYPE: (state, userType) => {
    state.userType = userType
  },
  SET_ROLES_NAME: (state, rolesName) => {
    state.rolesName = rolesName
  },
  SET_ROUTER_MENUS: (state, routerMenus) => {
    state.routerMenus = routerMenus
  }
}

const actions = {
  // user Login
  login({ commit }, userInfo) {
    const { username, password, type } = userInfo
    return new Promise((resolve, reject) => {
      login({ logName: username.trim(), logPassword: password, userType: type }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        commit('SET_NAME', data.userName)
        commit('SET_AVATAR', data.avatar)
        commit('SET_ACCOUNT', data.logName)
        commit('SET_CONTACT', data.contact)
        commit('SET_USER_TYPE', data.userType)
        commit('SET_ROLES_NAME', data.rolesName)
        commit('SET_ROUTER_MENUS', data.routerMenus)
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

        const { name, avatar } = data

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
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
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

