import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../components/HelloWorld')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../view/Login')
  },
  {
    path: '/resport',
    name: 'resport',
    component: () => import('../view/Resport')
  },
  {
    path: '/',
    name: 'home',
    redirect: '/home',
    component: ()=> import('../view/Manger'),
    children:[
      {
        path: 'home',name: '首页',component: ()=> import('../view/Home')
      },
      {
        path: 'user',name: '用户管理',component: ()=> import('../view/User')
      },
      {
        path: 'role',name: '角色管理',component: ()=> import('../view/Role')
      },
      /*{
        path: 'file',name: '文件管理',component: ()=> import('../view/File')
      }*/
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
