import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import AppIndex from "@/components/AppIndex"
import Login from "@/components/Login"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: "HelloWorld",
      component: HelloWorld
    },
    {
      path: '/index',
      name: 'AppIndex',
      component: AppIndex
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
