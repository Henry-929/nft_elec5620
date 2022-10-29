import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem('token') ? sessionStorage.getItem('token') : '',
    user: sessionStorage.getItem('user') ? sessionStorage.getItem('user') : '',
    userId: sessionStorage.getItem('userId') ? sessionStorage.getItem('userId') : '',
    balance: sessionStorage.getItem('balance') ? sessionStorage.getItem('balance') : '',
    bankCard: sessionStorage.getItem('bankCard') ? sessionStorage.getItem('balance') : '',
    marketNFTs: [],
    searchedMarketNFTs: [],
    selectNFT: {},
    shoppingCart: [],
    searchShow: true
  },
  mutations: {
    setToken (state, value) {
      state.token = value.token
      sessionStorage.setItem("token", state.token)     
    },
    setUser(state, value) {
      state.user = value.user
      state.userId = value.id
      state.balance = value.balance
      sessionStorage.setItem("user", state.user)
      sessionStorage.setItem("userId", state.userId)
      sessionStorage.setItem("balance", state.balance)
    },
    delToken (state) {
      state.token = ""
      state.user = ""
      state.userId = 0
      state.balance = 0
      sessionStorage.removeItem("token")
      sessionStorage.removeItem("user")
      sessionStorage.removeItem("userId")
      sessionStorage.removeItem("balance")
    },
    setBalance(state, value){
      state.balance = value
    },
    setSelectedNFT(state, value){
      state.selectNFT = value
    },
    setBankCard(state, value){
      state.bankCard = value
    },
    addToCart(state, value){
      state.shoppingCart.push(value)
    },
    deleteFromCart(state, value){
      state.shoppingCart = state.shoppingCart.filter(item => {
        return item.id !== value.id
      })
    },
    clearCart(state){
      state.shoppingCart = []
    },
    setMarketNFTs(state, value){
      state.marketNFTs = value
    },
    setSearchedMarketNFTs(state, value){
      state.searchedMarketNFTs = value
    },
    setSearchShow(state, value){
      state.searchShow = value
    }
  },
  actions: {
  },
  modules: {
  }
})
