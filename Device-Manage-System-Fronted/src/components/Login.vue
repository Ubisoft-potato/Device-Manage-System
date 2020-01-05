<template>

  <div>
    <el-image
      style="width: 400px; height: 170px"
      :src="require('../assets/cust.jpeg')"
      fit="contain"/>
    <el-form class="login-container" label-position="left"
             label-width="0px" v-loading="loading" element-loading-text="登录中...">
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
        <el-button type="primary" style="width: 30%;background: #409EFF;border: none; position: relative; right: 30px"
                   round>
          注册
        </el-button>
        <el-button type="primary" style="width: 30%;background: #409EFF;border: none; position: relative; left: 30px"
                   round
                   @click="login">
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
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
        }
      }
    },
    methods: {
      login() {
        const params = new URLSearchParams();
        params.append('username', this.loginForm.username)
        params.append('password', this.loginForm.password)
        this.loading = true
        this.$axios
          .post("/login", params)
          .then(res => {
            console.log(res.data)
            let data = JSON.parse(res.data);
            console.log(data)
            // if (data["httpCode"] === "200") {
            //   this.loading = false
            //   this.$message.success("登录成功")
            // }
            // if (data["httpCode"] === "401") {
            //   this.loading = false
            //   this.$message.error("账号或密码错误")
            // }
          }).catch(reason => {
          this.loading = false
          console.info(reason)
        })

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
