import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
// https://juejin.im/post/5b5bfd5b6fb9a04fdd7d687a
export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('@/pages/login/Login')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/pages/login/register')
    },
    {
      path: '/edit',
      name: 'myIndex',
      component: () => import('@/pages/MyIndex'),
      redirect: '/markdown',
      children: [
        {
          path: '/uedit/edit',
          name: 'uedit',
          component: () => import('@/components/UEdit')
        },
        {
          path: '/markdown',
          name: 'Editor',
          component: () => import('@/components/markdown')
        }
      ]
    }
  ]
})
