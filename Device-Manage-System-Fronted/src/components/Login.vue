<template>
  <body class="background">
  <el-image
    style="width: 400px; height: 170px"
    :src="require('../assets/cust.jpeg')"
    fit="contain"/>
  <el-form class="login-container" label-position="left"
           label-width="0px">
    <h3 class="login_title">系统登录</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号"/>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"/>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #0095ce;border: none" @click="login" v-loading="loading">
        登录
      </el-button>
    </el-form-item>
  </el-form>
  </body>

</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        loading: false,
        loginForm: {
          username: '',
          password: ''
        },
        responseResult: []
      }
    },
    methods: {
      login() {
        this.loading = true
        this.$axios
          .get("/v1/bpi/currentprice.json")
          .then(res => {
            console.log(res.data)
            if (res.data != null) {
              this.loading = false
            }
          })
        if (this.loginForm.username === "user" && this.loginForm.password === "123123") {
          this.$router.replace({path: '/index'});
        }
      }
    }
  }
</script>

<style scoped>
  .login-container {
    position: relative;
    top: -60px;
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
