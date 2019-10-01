
// initial state
// shape: [{ id, quantity }]
const state = {
  data: {},
  count: 'diary',
  showContent: {}
}

// getters
const getters = {
  getCount: (state) => {
    return state.count
  }
}

// actions
const actions = {
  setData (context, data) {
    context.commit('setDataFun', data)
  },
  getData (context) {
    context.commit('getDataFun')
  },
  setContent (context, content) {
    context.commit('setContentFun', content)
  },
  getContent (context) {
    context.commit('getContentFun')
  }
}

// mutations
const mutations = {
  setDataFun (state, data) {
    state.data = data
  },
  getDataFun (state) {
    return state.data
  },
  setContentFun (state, content) {
    state.showContent = content
  },
  getContentFun (state) {
    return state.showContent
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
