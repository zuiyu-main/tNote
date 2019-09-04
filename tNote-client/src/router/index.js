import Vue from 'vue'
import Router from 'vue-router'
import markdown from '@/components/markdown'
import UEdit from '@/components/UEdit'
import Index from '@/pages/MyIndex'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'myIndex',
      component: Index,
      redirect: '/markdown',
      children: [
        {
          path: '/uedit/edit',
          name: 'uedit',
          component: UEdit
        },
        {
          path: '/markdown',
          name: 'Editor',
          component: markdown
        }
      ]
    }
  ]
})
