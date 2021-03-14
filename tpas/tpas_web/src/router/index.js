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
    name: '公示',
    redirect: '/publicity',
    component: Layout,
    children: [
      {
        path: 'publicity',
        name: '公示',
        component: () => import('@/views/publicity/index'),
        meta: { title: '公示', icon: 'el-icon-chat-line-square' }
      }
    ]
  },

  {
    path: '/info',
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
    path: '/assessmentInfo',
    component: Layout,
    redirect: '/assessmentInfo/assessmentInfo-hour',
    alwaysShow: true,
    name: '教务信息',
    meta: { title: '教务信息', icon: 'el-icon-s-help', roles: [0, 1] },
    children: [
      {
        path: 'assessmentInfo-hour',
        name: '课时信息',
        component: () => import('@/views/assessmentInfo/course-hour'),
        meta: { title: '课时信息', icon: 'table', roles: [0, 1] }
      },
      {
        path: 'paper',
        name: '论文指导信息',
        component: () => import('@/views/assessmentInfo/paper'),
        meta: { title: '论文指导信息', icon: 'education', roles: [0, 1] }
      },
      {
        path: 'intern',
        name: '实习带队信息',
        component: () => import('@/views/assessmentInfo/intern'),
        meta: { title: '实习带队信息', icon: 'tree', roles: [0, 1] }
      },
      {
        path: 'other',
        name: '其他',
        component: () => import('@/views/assessmentInfo/others'),
        meta: { title: '其他', icon: 'el-icon-files', roles: [0, 1] }
      },
      {
        path: 'taskManage',
        name: '任务待办',
        component: () => import('@/views/taskManage/index'),
        meta: { title: '任务待办', icon: 'el-icon-date', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/taskManage',
    meta: {
      title: '教务管理', icon: 'el-icon-date', roles: [0, 1]
    },
    name: '教务管理',
    redirect: '/taskManage',
    component: Layout,
    children: [
      {
        path: 'taskManage',
        name: '任务分配',
        component: () => import('@/views/taskManage/index'),
        meta: { title: '任务分配', icon: 'el-icon-date', roles: [0, 1] }
      },
      {
        path: 'aduitManage',
        name: '修改审批',
        component: () => import('@/views/aduitManage/index'),
        meta: { title: '修改审批', icon: 'el-icon-document-copy', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/modify',
    component: Layout,
    redirect: '/modify/modifying',
    name: '我的修改申请',
    meta: {
      title: '我的修改', icon: 'nested', roles: [0, 1]
    },
    children: [
      {
        path: 'modifying',
        component: () => import('@/views/modify/modifying'),
        name: '进行中',
        meta: { title: '进行中', icon: 'el-icon-document-remove', roles: [0, 1] }
      },
      {
        path: 'finished',
        component: () => import('@/views/modify/finished'), // Parent router-view
        name: '已完成',
        meta: { title: '已完成', icon: 'el-icon-document-checked', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/assess',
    meta: {
      title: '考核结果', icon: 'el-icon-data-line', roles: [0, 1]
    },
    name: '考核结果',
    redirect: '/assess/score',
    component: Layout,
    children: [
      {
        path: 'score',
        name: '考核成绩',
        component: () => import('@/views/assess/score'),
        meta: { title: '考核成绩', icon: 'el-icon-finished', roles: [0, 1] }
      },
      {
        path: 'analysis',
        name: '汇总分析',
        component: () => import('@/views/assess/analysis'),
        meta: { title: '汇总分析', icon: 'el-icon-data-line', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/systemManage',
    meta: {
      title: '系统管理', icon: 'el-icon-set-up', roles: [1]
    },
    name: '系统管理',
    redirect: '/systemManage',
    component: Layout,
    children: [
      {
        path: 'systemManage',
        name: '绩效规则管理',
        component: () => import('@/views/systemManage/rules-manage'),
        meta: { title: '考核规则管理', icon: 'el-icon-odometer', roles: [1] }
      },
      {
        path: 'courseManage',
        name: '课程管理',
        component: () => import('@/views/systemManage/course-manage'),
        meta: { title: '课程管理', icon: 'el-icon-document', roles: [1] }
      },
      {
        path: 'majorManage',
        name: '专业管理',
        component: () => import('@/views/systemManage/major-manage'),
        meta: { title: '专业管理', icon: 'el-icon-office-building', roles: [1] }
      },
      {
        path: 'publicity',
        name: '公示管理',
        component: () => import('@/views/publicity/index'),
        meta: { title: '公示管理', icon: 'el-icon-chat-line-square', roles: [1] }
      }
    ]
  },

  {
    path: '/userAndRolesManage',
    meta: {
      title: '用户及权限管理', icon: 'user', roles: [1]
    },
    name: '用户管理',
    redirect: '/userAndRolesManage/user-manage',
    component: Layout,
    children: [
      {
        path: 'user-manage',
        name: '用户管理',
        component: () => import('@/views/userAndRolesManage/user-manage'),
        meta: { title: '用户管理', icon: 'user', roles: [1] }
      },
      {
        path: 'roles-manage',
        name: '角色权限管理',
        component: () => import('@/views/userAndRolesManage/roles-manage'),
        meta: { title: '角色权限管理', icon: 'el-icon-unlock', roles: [1] }
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
