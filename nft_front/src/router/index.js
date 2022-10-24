import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../view/Home/Home.vue'
import Detail from '../view/Detail/Index.vue'
import ShoppingCart from '../view/ShoppingCart/Index.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/detail',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/shoppingCart',
    name: 'ShoppingCart',
    component: ShoppingCart
  },
  // component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
]

const router = new VueRouter({
  routes
})

export default router
