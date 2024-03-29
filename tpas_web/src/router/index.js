import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * 默认路由列表
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/info',
    children: [
      {
        path: 'info',
        name: '个人中心',
        component: () => import('@/views/info/index'),
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  }
]

/**
 * asyncRoutes
 * 权限校验路由列表
 */
export const asyncRoutes = [

  {
    path: '/course',
    component: Layout,
    redirect: '/course/course-hour',
    alwaysShow: true,
    name: '教学管理',
    meta: { title: '教学管理', icon: 'el-icon-s-help', roles: [0, 1] },
    children: [
      {
        path: 'course-hour',
        name: '课时信息',
        component: () => import('@/views/course/course-hour'),
        meta: { title: '课时信息', icon: 'table', roles: [0, 1] }
      },
      {
        path: 'paper',
        name: '论文指导信息',
        component: () => import('@/views/course/paper'),
        meta: { title: '论文指导信息', icon: 'education', roles: [0, 1] }
      },
      {
        path: 'job',
        name: '实习带队信息',
        component: () => import('@/views/course/job'),
        meta: { title: '实习带队信息', icon: 'tree', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/modify',
    component: Layout,
    redirect: '/modify/finished',
    name: '我的修改',
    meta: {
      title: '我的修改', icon: 'nested', roles: [0, 1]
    },
    children: [
      {
        path: 'finished',
        component: () => import('@/views/modify/finished'), // Parent router-view
        name: '已完成',
        meta: { title: '已完成', icon: 'clipboard', roles: [0, 1] }
      },
      {
        path: 'modifying',
        component: () => import('@/views/modify/modifying'),
        name: '进行中',
        meta: { title: '进行中', icon: 'list', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/assess',
    meta: {
      title: '考核结果', icon: 'form', roles: [0, 1]
    },
    name: '考核结果',
    redirect: '/assess',
    component: Layout,
    children: [
      {
        path: 'assess',
        name: '考核结果',
        component: () => import('@/views/assess/index'),
        meta: { title: '考核结果', icon: 'form', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/courseManage',
    meta: {
      title: '课程管理', icon: 'form', roles: [1]
    },
    name: '课程管理',
    redirect: '/courseManage',
    component: Layout,
    children: [
      {
        path: 'courseManage',
        name: '课程管理',
        component: () => import('@/views/courserManage/index'),
        meta: { title: '课程管理', icon: 'form', roles: [1] }
      }
    ]
  },

  {
    path: '/userManage',
    meta: {
      title: '用户管理', icon: 'form', roles: [1]
    },
    name: '用户管理',
    redirect: '/userManage',
    component: Layout,
    children: [
      {
        path: 'userManage',
        name: '用户管理',
        component: () => import('@/views/userManage/index'),
        meta: { title: '用户管理', icon: 'form', roles: [1] }
      }
    ]
  },

  {
    path: '/aduitManage',
    meta: {
      title: '操作记录', icon: 'form', roles: [0, 1]
    },
    name: '操作记录',
    redirect: '/aduitManage',
    component: Layout,
    children: [
      {
        path: 'aduitManage',
        name: '操作记录',
        component: () => import('@/views/aduitManage/index'),
        meta: { title: '操作记录', icon: 'form', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/systemManage',
    meta: {
      title: '系统管理', icon: 'form', roles: [1]
    },
    name: '系统管理',
    redirect: '/systemManage',
    component: Layout,
    children: [
      {
        path: 'systemManage',
        name: '系统管理',
        component: () => import('@/views/systemManage/index'),
        meta: { title: '系统管理', icon: 'form', roles: [1] }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', name: '404', hidden: true, meta: {}}

]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
