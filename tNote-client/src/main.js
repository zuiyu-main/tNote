// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// ueditor
import '../static/UE/ueditor.config.js'
import '../static/UE/ueditor.all.min.js'
import '../static/UE/lang/zh-cn/zh-cn.js'
import '../static/UE/ueditor.parse.min.js'
import * as socketApi from './utils/socket/WebSocket'
import { post, get } from '@/utils/request/axios'
import store from './store'

Vue.prototype.$post = post
Vue.prototype.$get = get
Vue.prototype.socketApi = socketApi
Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(mavonEditor)
router.beforeEach((to, from, next) => {
  if (to.fullPath !== '/' && to.fullPath !== '/404' && to.fullPath !== '/register') {
    if (localStorage.getItem('token') === null) {
      next({
        path: '/login'
      })
      return
    }
  }
  next()
})
let vm = new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})

Vue.use({
  vm
})
