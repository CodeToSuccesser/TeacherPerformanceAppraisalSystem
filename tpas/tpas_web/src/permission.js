import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import { generateRoutes } from '@/store/modules/permission'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/Login'] // no redirect whitelist

// 进入一个页面时会触发该事件
// 即将要进入的路由
// 正要离开的路由
router.beforeEach(async(to, from, next) => {
  // 加载进度
  NProgress.start()

  // 设置页面标题
  document.title = getPageTitle(to.meta.title)

  // 此处进行鉴权操作
  const token = getToken()

  if (token) {
    if (to.path === '/login') {
      // 系统根路由
      Message('您已登录,如需切换用户请退出重新登录')
      next({ path: '/' })
      NProgress.done()
    } else {
      const addRoutes = store.getters.addRoutes
      if (addRoutes === undefined || addRoutes.length <= 0) {
        try {
          // 根据用户角色加载路由
          const roles = (store.getters.rolesName === null || store.getters.rolesName === []) ? sessionStorage.getItem('rolesName') : store.getters.rolesName
          const routerMenus = (store.getters.routerMenus === null || store.getters.routerMenus === []) ? sessionStorage.getItem('routerMenus') : store.getters.routerMenus
          generateRoutes(roles, routerMenus)
          router.$addRoutes(store.getters.routes)
          router.options.routes = store.getters.routes
          next({ ...to, replace: true })
        } catch (error) {
          // 重置token并跳转到登录页面重新登录
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login`)
          NProgress.done()
        }
      } else {
        next()
      }
    }
  } else {
    // 没有token的情况
    if (whiteList.indexOf(to.path) !== -1) {
      // 免登陆列表，直接跳转
      next()
    } else {
      // 跳转到登录页需重新登录
      next(`/login`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
