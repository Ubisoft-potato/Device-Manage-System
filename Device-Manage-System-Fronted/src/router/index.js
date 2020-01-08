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
import DeviceTable from "../components/device/DeviceTable";
import DeviceCheck from "../components/device/DeviceCheck";
import AddDevice from "../components/device/AddDevice";

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
          children: [
            {
              path: '/deviceTable',
              name: 'DeviceTable',
              component: DeviceTable,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/deviceCheck',
              name: 'DeviceCheck',
              component: DeviceCheck,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/addDevice',
              name: 'AddDevice',
              component: AddDevice,
              meta: {
                requireAuth: true
              }
            }
          ],
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
