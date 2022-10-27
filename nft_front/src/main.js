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

Vue.filter('dateFormat', function(originVal){
  const dt  = new Date(originVal)
  const y = dt.getFullYear()
  const m = (dt.getMonth() + 1 + '').padStart(2, '0')
  const d = (dt.getDay()+ '').padStart(2, '0')
  const hh = (dt.getHours()+ '').padStart(2, '0')
  const mm = (dt.getMinutes()+ '').padStart(2, '0')
  const ss = (dt.getSeconds()+ '').padStart(2, '0')

  return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
