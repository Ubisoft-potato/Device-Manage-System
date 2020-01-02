import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from "@/components/AppIndex"
import Login from "@/components/Login"
import Admin from "@/components/admin/Admin.vue"

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      name: "Index",
      redirect: '/index'
    },
    {
      path: '/index',
      name: 'AppIndex',
      component: AppIndex,
      children: [
        {
        path: '/admin',
        name: 'admin',
        component: Admin
      }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
