import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  },
  mutations: {
    setToken (state, token) {
      state.token = token;
      localStorage.setItem("token", state.token);     //存储token
      console.log(state.token);
    },
    delToken (state) {
      state.token = '';
      localStorage.removeItem("token");    //删除token
    }
  },
  actions: {
  },
  modules: {
  }
})
