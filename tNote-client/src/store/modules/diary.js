
// initial state
// shape: [{ id, quantity }]
const state = {
  data: { id: '6666' },
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
  }
}

// mutations
const mutations = {
  setDataFun (state, data) {
    state.data = data
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
