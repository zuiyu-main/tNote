import Vue from 'vue'
import Router from 'vue-router'
import Editor from '@/components/editor'
import UEdit from '@/components/UEdit'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Editor',
      component: Editor
    },{
      path: '/uedit/edit',
      name:'uedit',
      component: UEdit
    },
    {
      path: '/markdown',
      name: 'Editor',
      component: Editor
    }
  ]
})
