import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'

Vue.use(Router)

export var constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  // {
  //   path: '/register',
  //   component: () => import('@/views/login/register'),
  //   hidden: true
  // },

  // {
  //   path: '/findback',
  //   component: () => import('@/views/login/findback'),
  //   hidden: true
  // },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/good',
    children: [{
      path: 'good',
      name: 'good',
      component: () => import('@/views/good.vue'),
      meta: { title: '商品管理', icon: 'el-icon-goods' }
    }]
  },

  {
    path: '/order',
    component: Layout,
    children: [{
      path: 'order',
      component: () => import('@/views/order'),
      meta: { title: '订单管理', icon: 'el-icon-s-order' }
    }]
  },

  {
    path: '/caigou',
    component: Layout,
    children: [{
      path: 'caigou',
      component: () => import('@/views/caigou'),
      meta: { title: '采购管理', icon: 'el-icon-bell' }
    }]
    // hidden: localStorage.getItem('role') === '1'
  },

  {
    path: '/tongji',
    component: Layout,
    children: [{
      path: 'tongji',
      component: () => import('@/views/tongji'),
      meta: { title: '统计管理', icon: 'el-icon-coin' }
    }]
    // hidden: localStorage.getItem('role') === '2' || localStorage.getItem('role') === '3'
  },

  {
    path: '/vip',
    component: Layout,
    children: [{
      path: 'vip',
      component: () => import('@/views/vip'),
      meta: { title: '会员管理', icon: 'el-icon-user-solid' }
    }]
    // 用户
    // hidden: localStorage.getItem('role') === '2' || localStorage.getItem('role') === '3'
  },

  {
    path: '/user',
    component: Layout,
    children: [{
      path: 'user',
      component: () => import('@/views/user'),
      meta: { title: '员工管理', icon: 'el-icon-user-solid' }
    }]
    // 管理员
    // hidden: localStorage.getItem('role') === '1' || localStorage.getItem('role') === '2'
  },

  {
    path: '/total',
    component: Layout,
    children: [{
      path: 'total',
      component: () => import('@/views/total'),
      meta: { title: '销售统计', icon: 'el-icon-s-shop' }
    }]
    // 员工 + 管理员
    // hidden: localStorage.getItem('role') === '1'
  },

  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = (result) => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}

export default router

