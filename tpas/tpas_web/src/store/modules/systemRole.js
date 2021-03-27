import { querySystemMenus } from '@/api/systemRole'

const getDefaultState = () => {
  return {
    systemMenus: [],
    systemPermission: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_SYSTEM_MENUS: (state, list) => {
    state.systemMenus = list
  },
  SET_SYSTEM_PERMISSION: (state, list) => {
    state.systemPermission = list
  }
}

const actions = {
  // 获取系统目录
  querySystemMenus({ commit }) {
    return new Promise((resolve, reject) => {
      querySystemMenus().then(response => {
        const { data } = response
        const { menuModelList, permissionModelList } = data
        commit('SET_SYSTEM_MENUS', menuModelList)
        commit('SET_SYSTEM_PERMISSION', permissionModelList)
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
