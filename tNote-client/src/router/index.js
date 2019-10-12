import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
// https://juejin.im/post/5b5bfd5b6fb9a04fdd7d687a
export default new Router({
  mode: 'hash',
  routes: [
    {
      path: '/',
      name: '/',
      component: () => import('@/pages/login/Login')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/pages/login/Login')
    },
    {
      path: '/index',
      name: 'diary',
      component: () => import('@/pages/MyIndex')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/pages/login/register')
    },
    {
      path: '/create',
      name: 'create',
      component: () => import('@/pages/diary/CreateDiary')
    },
    {
      path: '/404',
      name: 'notFound',
      component: () => import('@/pages/404/NotFound')
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
          component: () => import('@/components/MarkDown')
        }

      ]
    },
    {
      path: '*', // 此处需特别注意置于最底部
      redirect: '/404'
    }
  ]
})
