import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from "@/components/AppIndex"
import Login from "@/components/Login"
import UserCenter from "@/components/user/UserCenter"
import Home from "@/components/Home"
import Register from "../components/Register";
import UserManage from "../components/user/UserManage";
import DeviceManage from "../components/device/DeviceManage";
import DeviceTable from "../components/device/DeviceTable";
import DeviceCheck from "../components/device/DeviceCheck";
import AddDevice from "../components/device/AddDevice";
import PublicTable from "../components/device/PublicTable";
import DeviceReservation from "../components/device/DeviceReservation";
import ReservationHistory from "../components/device/ReservationHistory";
import DeviceSearch from "../components/device/DeviceSearch";
import CostSettlementManage from "../components/device/CostSettlementManage";
import ResetPassword from "../components/user/ResetPassword";

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
          children: [
            {
              path: '/publicTable',
              name: 'PublicTable',
              component: PublicTable,
            },
            {
              path: '/deviceReservation',
              name: 'DeviceReservation',
              component: DeviceReservation,
            },
            {
              path: '/deviceSearch',
              name: 'DeviceSearch',
              component: DeviceSearch,
            }
          ],
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/userCenter',
          name: 'UserCenter',
          component: UserCenter,
          children: [{
            path: '/resetPassword',
            name: ResetPassword,
            component: ResetPassword,
            meta: {
              requireAuth: true
            }
          }],
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
          path: '/reservationHistory',
          name: 'ReservationHistory',
          component: ReservationHistory,
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
            },
            {
              path: '/costSettlementManage',
              name: 'CostSettlementManage',
              component: CostSettlementManage,
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
