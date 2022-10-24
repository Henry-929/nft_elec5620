import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem('token') ? sessionStorage.getItem('token') : '',
    user: '',
    userId: 0,
    balance: 0,
    marketNFTs: [],
    searchedMarketNFTs: [],
    selectNFT: {},
    shoppingCart: []
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
    },
    setBalance(state, value){
      state.balance = value
    },
    setSelectedNFT(state, value){
      state.selectNFT = value
    },
    addToCart(state, value){
      state.shoppingCart.push(value)
    },
    deleteFromCart(state, value){
      state.shoppingCart = state.shoppingCart.filter(item => {
        return item.id !== value.id
      })
    },
    clearCart(){
      state.shoppingCart = []
    },
    setMarketNFTs(state, value){
      state.marketNFTs = value
    },
    setSearchedMarketNFTs(state, value){
      state.searchedMarketNFTs = value
    }
  },
  actions: {
  },
  modules: {
  }
})
