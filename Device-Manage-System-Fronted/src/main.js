// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import store from './store'
import {MessageBox} from "element-ui";

//请求根域名
axios.defaults.baseURL = "http://localhost:8081"
axios.defaults.withCredentials = true
// 全局注册axios客户端
Vue.prototype.$axios = axios
Vue.use(ElementUI)
Vue.config.productionTip = false


router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) {
      if (store.state.user !== null) {
        console.log(store.state.user)
        next()
      } else {
        next({
          path: 'login',
          query: {redirect: to.fullPath}
        })
      }
    } else {
      next()
    }
  }
)

axios.interceptors.response.use(
  response => {
    if (response.status === 200) {
      if (response.data["httpCode"] === "403") {
        MessageBox.alert(response.data["message"], "用户验证", {
          confirmButtonText: "确定"
        })
      }
      return Promise.resolve(response);
    } else {
      return Promise.reject(response);
    }
  },
  error => {
    console.log(error.response.status === 401)
    if (error.response.status === 401) {
      console.log("未登录")
      MessageBox.alert("登录超时，请重新登录!", "用户验证", {
        confirmButtonText: "前往登录页",
        callback: action => {
          window.location.href = "/login"
        }
      })
    }
  })

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App),
  components: {App},
  template: '<App/>'
})


