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
      meta: { title: '销售单', icon: 'el-icon-s-order' }
    }],
    // 销售
    hidden: localStorage.getItem('role') === '1' || localStorage.getItem('role') === '3' || localStorage.getItem('role') === '4'
  },

  {
    path: '/stock',
    component: Layout,
    children: [{
      path: 'stock',
      component: () => import('@/views/stock'),
      meta: { title: '库存管理', icon: 'el-icon-s-order' }
    }],
    // 销售、库存
    hidden: localStorage.getItem('role') === '1' || localStorage.getItem('role') === '4'
  },

  {
    path: '/caigou',
    component: Layout,
    children: [{
      path: 'caigou',
      component: () => import('@/views/caigou'),
      meta: { title: '采购管理', icon: 'el-icon-bell' }
    }],
    // 经理
    hidden: localStorage.getItem('role') === '2'
  },

  {
    path: '/vip',
    component: Layout,
    children: [{
      path: 'vip',
      component: () => import('@/views/vip'),
      meta: { title: '会员管理', icon: 'el-icon-user-solid' }
    }],
    // 销售
    hidden: localStorage.getItem('role') === '1' || localStorage.getItem('role') === '3' || localStorage.getItem('role') === '4'
  },

  {
    path: '/user',
    component: Layout,
    children: [{
      path: 'user',
      component: () => import('@/views/user'),
      meta: { title: '员工管理', icon: 'el-icon-user-solid' }
    }],
    // 经理
    hidden: localStorage.getItem('role') === '2' || localStorage.getItem('role') === '3' || localStorage.getItem('role') === '4'
  },

  {
    path: '/supplier',
    component: Layout,
    children: [{
      path: 'supplier',
      component: () => import('@/views/supplier'),
      meta: { title: '供货商', icon: 'el-icon-s-shop' }
    }],
    // 采购
    hidden: localStorage.getItem('role') === '1' || localStorage.getItem('role') === '2' || localStorage.getItem('role') === '3'
  },

  {
    path: '/tongji',
    component: Layout,
    children: [{
      path: 'tongji',
      component: () => import('@/views/tongji'),
      meta: { title: '统计管理', icon: 'el-icon-coin' }
    }],
    // 经理
    hidden: localStorage.getItem('role') === '2' || localStorage.getItem('role') === '3' || localStorage.getItem('role') === '4'
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

