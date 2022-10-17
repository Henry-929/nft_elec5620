import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.config.productionTip = false
Vue.use(ElementUI);

import axios from 'axios'
Vue.prototype.$axios = axios
Vue.prototype.apiUrl = 'http://localhost:8182'

axios.interceptors.request.use(config=>{
  if (store.state.token) {
    config.headers.Authorization = store.state.token
	}
  return config
}, error => {
	return Promise.reject(error);
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
