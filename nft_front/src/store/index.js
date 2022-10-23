import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem('token') ? sessionStorage.getItem('token') : '',
    user: '',
    userId: 0
  },
  mutations: {
    setToken (state, token) {
      state.token = token.token
      sessionStorage.setItem("token", state.token)     //存储token
    },
    setUser(state, value) {
      state.user = value.user
      state.userId = value.id
    },
    delToken (state) {
      state.token = ''
      sessionStorage.removeItem("token")    //删除token
    }
  },
  actions: {
  },
  modules: {
  }
})
