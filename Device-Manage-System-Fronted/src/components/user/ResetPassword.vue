<template>

  <div>
    <el-card class="reset-container"
             v-loading="loading"
             element-loading-text="修改中...">
      <h3 class="reset_title">修改密码</h3>
      <el-form status-icon label-width=auto :model="passwordReset" ref="passwordReset" :rules="rules">
        <el-form-item label='旧密码' prop="oldPassword">
          <el-input type="password"
                    show-password
                    placeholder="请输入旧密码" v-model="passwordReset.oldPassword"/>
        </el-form-item>
        <el-form-item label='新密码' prop="newPassword">
          <el-input type="password"
                    show-password
                    placeholder="请输入新密码" v-model="passwordReset.newPassword"/>
        </el-form-item>
        <el-form-item label='再次输入新密码' prop="confirmNewPassword">
          <el-input type="password"
                    show-password
                    placeholder="请再次输入新密码" v-model="passwordReset.confirmNewPassword"/>
        </el-form-item>
        <el-form-item style="position: relative; top: 20px">
          <el-button style="position:relative; left: -80px" type="primary" @click="submitForm('passwordReset')">确认修改
          </el-button>
          <el-button type="danger" @click="resetForm('passwordReset')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>

  export default {
    name: "ResetPassword",
    data() {
      const validateNull = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入旧密码'));
        } else {
          callback()
        }
      }
      const validateNewPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'));
        } else {
          if (this.passwordReset.newPassword !== '') {
            this.$refs.passwordReset.validateField('confirmNewPassword');
          }
          callback();
        }
      };
      const confirmNewPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.passwordReset.newPassword) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        loading: false,
        passwordReset: {
          oldPassword: "",
          newPassword: "",
          confirmNewPassword: "",
        },
        rules: {
          oldPassword: [{validator: validateNull, trigger: 'blur'}],
          newPassword: [{validator: validateNewPass, trigger: 'blur'}],
          confirmNewPassword: [{validator: confirmNewPassword, trigger: 'blur'}],
        }
      }
    },
    methods: {
      submitForm(passwordReset) {
        this.$refs[passwordReset].validate((valid) => {
          if (valid) {
            this.$axios.put("users/changePassword", this.passwordReset)
              .then(res => {
                if (res.data === true) {
                  this.$messageBox.alert("密码修改成功，请重新登录", "用户验证", {
                    confirmButtonText: "前往登录页",
                    callback: action => {
                      window.localStorage.clear()
                      window.location.href = "/login"
                    }
                  })
                } else if (res.data === false) {
                  this.$message({
                    showClose: true,
                    message: "修改密码失败，请稍后再试",
                    type: "error"
                  })
                }
              })
              .catch(error => {

              })
          }
        })
      },
      resetForm(passwordReset) {
        this.$refs[passwordReset].resetFields();
      }
    }
  }
</script>

<style>
  .reset-container {
    position: relative;
    top: 10px;
    left: -50px;
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .reset_title {
    margin: 0 auto 40px auto;
    text-align: center;
    bottom: 10px;
    color: #505458;
  }
</style>
