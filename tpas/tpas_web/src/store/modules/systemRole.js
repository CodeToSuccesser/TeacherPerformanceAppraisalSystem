import { querySystemMenus } from '@/api/systemRole'

const getDefaultState = () => {
  return {
    systemMenus: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_SYSTEM_MENUS: (state, list) => {
    state.systemMenus = list
  }
}

const actions = {
  // 获取系统目录
  querySystemMenus({ commit }) {
    return new Promise((resolve, reject) => {
      querySystemMenus().then(response => {
        const { data } = response
        console.log('querySystemMenus: ', data)
        commit('SET_SYSTEM_MENUS', data)
        resolve()
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
