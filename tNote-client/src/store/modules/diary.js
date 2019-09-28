
// initial state
// shape: [{ id, quantity }]
const state = {
  data: {},
  count: 'diary'
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
  }
}

// mutations
const mutations = {
  setDataFun (state, data) {
    state.data = data
  },
  getDataFun (state) {
    return state.data
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
