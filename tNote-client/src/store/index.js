import Vue from 'vue'
import Vuex from 'vuex'
import diary from './modules/diary'
import bak from './modules/bak'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    diary,
    bak
  },
  strict: debug
})
