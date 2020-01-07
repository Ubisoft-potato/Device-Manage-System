import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from "@/components/AppIndex"
import Login from "@/components/Login"
import UserCenter from "@/components/user/UserCenter"
import Home from "@/components/Home"
import Register from "../components/Register";
import UserManage from "../components/user/UserManage";
import WaitForCheck from "../components/device/WaitForCheck";
import DeviceManage from "../components/device/DeviceManage";

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/register',
      name: "Register",
      component: Register
    },
    {
      path: '/',
      name: "Index",
      redirect: '/home',
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/index',
      name: 'AppIndex',
      component: AppIndex,
      children: [
        {
          path: '/home',
          name: 'home',
          component: Home,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/userCenter',
          name: 'UserCenter',
          component: UserCenter,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/userManage',
          name: 'UserManage',
          component: UserManage,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/waitForCheck',
          name: 'WaitForCheck',
          component: WaitForCheck,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/deviceManage',
          name: 'DeviceManage',
          component: DeviceManage,
          meta: {
            requireAuth: true
          }
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
