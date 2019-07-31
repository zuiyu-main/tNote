import Vue from 'vue'
import Router from 'vue-router'
import markdown from '@/components/markdown'
import UEdit from '@/components/UEdit'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Editor',
      component: markdown
    }, {
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
})
