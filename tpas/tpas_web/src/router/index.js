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
    path: '/Login',
    component: () => import('@/views/Login/index'),
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
    redirect: '/Publicity',
    component: Layout,
    children: [
      {
        path: 'Publicity',
        name: '公示',
        component: () => import('@/views/Publicity/index'),
        meta: { title: '公示', icon: 'el-icon-chat-line-square' }
      }
    ]
  },

  {
    path: '/Info',
    component: Layout,
    redirect: '/Info',
    children: [
      {
        path: 'info',
        name: '个人中心',
        component: () => import('@/views/Info/index'),
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
    path: '/AssessmentInfo',
    component: Layout,
    redirect: '/AssessmentInfo/AssessmentInfo-hour',
    alwaysShow: true,
    name: '教务信息',
    meta: { title: '教务信息', icon: 'el-icon-s-help', roles: [0, 1] },
    children: [
      {
        path: 'course-hour',
        name: '课时信息',
        component: () => import('@/views/AssessmentInfo/CourseHour'),
        meta: { title: '课时信息', icon: 'table', roles: [0, 1] }
      },
      {
        path: 'paper',
        name: '论文指导信息',
        component: () => import('@/views/AssessmentInfo/Paper'),
        meta: { title: '论文指导信息', icon: 'education', roles: [0, 1] }
      },
      {
        path: 'intern',
        name: '实习带队信息',
        component: () => import('@/views/AssessmentInfo/Intern'),
        meta: { title: '实习带队信息', icon: 'tree', roles: [0, 1] }
      },
      {
        path: 'task',
        name: '任务待办',
        component: () => import('@/views/AssessmentInfo/Task'),
        meta: { title: '任务待办', icon: 'el-icon-date', roles: [0, 1] }
      },
      {
        path: 'others',
        name: '其他',
        component: () => import('@/views/AssessmentInfo/Others'),
        meta: { title: '其他', icon: 'el-icon-files', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/AffairManage',
    meta: {
      title: '教务管理', icon: 'el-icon-date', roles: [0, 1]
    },
    name: '教务管理',
    redirect: '/AffairManage',
    component: Layout,
    children: [
      {
        path: 'taskManage',
        name: '任务分配',
        component: () => import('@/views/AffairManage/TaskManage'),
        meta: { title: '任务分配', icon: 'el-icon-date', roles: [0, 1] }
      },
      {
        path: 'AffairManage',
        name: '修改审批',
        component: () => import('@/views/AffairManage/AduitManage'),
        meta: { title: '修改审批', icon: 'el-icon-document-copy', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/Modify',
    component: Layout,
    redirect: '/Modify/modifying',
    name: '我的修改申请',
    meta: {
      title: '我的修改', icon: 'nested', roles: [0, 1]
    },
    children: [
      {
        path: 'modifying',
        component: () => import('@/views/Modify/Modifying'),
        name: '进行中',
        meta: { title: '进行中', icon: 'el-icon-document-remove', roles: [0, 1] }
      },
      {
        path: 'finished',
        component: () => import('@/views/Modify/Finished'), // Parent router-view
        name: '已完成',
        meta: { title: '已完成', icon: 'el-icon-document-checked', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/Assess',
    meta: {
      title: '考核结果', icon: 'el-icon-data-line', roles: [0, 1]
    },
    name: '考核结果',
    redirect: '/Assess/score',
    component: Layout,
    children: [
      {
        path: 'score',
        name: '考核成绩',
        component: () => import('@/views/Assess/Score'),
        meta: { title: '考核成绩', icon: 'el-icon-finished', roles: [0, 1] }
      },
      {
        path: 'analysis',
        name: '汇总分析',
        component: () => import('@/views/Assess/Analysis'),
        meta: { title: '汇总分析', icon: 'el-icon-data-line', roles: [0, 1] }
      }
    ]
  },

  {
    path: '/SystemManage',
    meta: {
      title: '系统管理', icon: 'el-icon-set-up', roles: [1]
    },
    name: '系统管理',
    redirect: '/SystemManage',
    component: Layout,
    children: [
      {
        path: 'SystemManage',
        name: '绩效规则管理',
        component: () => import('@/views/SystemManage/RulesManage'),
        meta: { title: '考核规则管理', icon: 'el-icon-odometer', roles: [1] }
      },
      {
        path: 'courseManage',
        name: '课程管理',
        component: () => import('@/views/SystemManage/CourseManage'),
        meta: { title: '课程管理', icon: 'el-icon-document', roles: [1] }
      },
      {
        path: 'majorManage',
        name: '专业管理',
        component: () => import('@/views/SystemManage/MajorManage'),
        meta: { title: '专业管理', icon: 'el-icon-office-building', roles: [1] }
      },
      {
        path: 'Publicity',
        name: '公示管理',
        component: () => import('@/views/SystemManage/PublicityManage'),
        meta: { title: '公示管理', icon: 'el-icon-chat-line-square', roles: [1] }
      }
    ]
  },

  {
    path: '/UserAndRolesManage',
    meta: {
      title: '用户及权限管理', icon: 'user', roles: [1]
    },
    name: '用户管理',
    redirect: '/UserAndRolesManage/user-manage',
    component: Layout,
    children: [
      {
        path: 'user-manage',
        name: '用户管理',
        component: () => import('@/views/UserAndRolesManage/UserManage'),
        meta: { title: '用户管理', icon: 'user', roles: [1] }
      },
      {
        path: 'roles-manage',
        name: '角色权限管理',
        component: () => import('@/views/UserAndRolesManage/RolesManage'),
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
