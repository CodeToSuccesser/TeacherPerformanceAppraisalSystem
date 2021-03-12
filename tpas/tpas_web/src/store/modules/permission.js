import { asyncRoutes, constantRoutes } from '@/router'
import { string2List } from '@/utils/index.js'

/**
 * Use meta.role to determine if the current user has permission
 * @param role
 * @param route
 */
function hasPermission(roles, route) {
  const roleList = string2List(roles)
  if (route.meta && route.meta.roles) {
    // return route.meta.roles.indexOf(parseInt(role)) > -1
    return roleList.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param role
 */
export function filterAsyncRoutes(routes, role) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(role, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, role)
      }
      res.push(tmp)
    }
  })
  return res
}


/**
 * 后台查询的菜单数据拼装成路由格式的数据
 * https://blog.csdn.net/acoolper/article/details/97136553
 * @param routes
 */
export function generaMenu(routes, routeMenu, parent) {
  routeMenu.forEach(item => {
    if (item.path.replace("/", "") !== 'info') {
      const menu = {
        path: item.level === 0 ? item.path : item.path.replace("/", "") ,
        component: item.level === 0 ? require('@/layout').default : require(`@/views/${item.parentValue}${item.path}`).default,
        // : item.level === 0 ? Layout : resolve => require([`@/views/${item.parentValue}${item.path}`], resolve),
        // hidden: true,
        children: [],
        name: item.name,
        meta: { title: item.meta.title, icon: item.meta.icon, roles: item.meta.roles }
      }
      if (item.children) {
        generaMenu(menu.children, item.children, menu.path)
      }
      routes.push(menu)
    }
  })
}

export function generateRoutes(roles, routerMenus) {
  // const { roles, routerMenus } = data
  const loadMenuData = []
  Object.assign(loadMenuData, routerMenus)
  generaMenu(asyncRoutes, loadMenuData, "")
  asyncRoutes.push({ path: '*', redirect: '/404', hidden: true })
  const accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
  mutations.SET_ROUTES(state, accessedRoutes)
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, data) {
    const { roles, routerMenus } = data
    return new Promise(resolve => {
      // const accessedRoutes = filterAsyncRoutes(asyncRoutes, role)
      // commit('SET_ROUTES', accessedRoutes)
      // resolve(accessedRoutes)
      const loadMenuData = []
      Object.assign(loadMenuData, routerMenus)
      generaMenu(asyncRoutes, loadMenuData, "")
      const accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      commit('SET_ROUTES', accessedRoutes)
      console.log("commit('SET_ROUTES', accessedRoutes)")
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  data() {
    return {}
  }
}
