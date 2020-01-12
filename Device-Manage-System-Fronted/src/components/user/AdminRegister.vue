<template>
  <el-card class="admin-register-container" v-loading="loading" element-loading-text="添加中...">
    <h3 class="register_title">添加管理员</h3>
    <el-form status-icon label-width=auto :model="user" ref="user" :rules="rules">
      <el-form-item label='账号' prop="username">
        <el-input placeholder="请输入账号" v-model="user.username"/>
      </el-form-item>
      <el-form-item label='密码' prop="password">
        <el-input placeholder="请输入密码" v-model="user.password" type="password"/>
      </el-form-item>
      <el-form-item label='确认密码' prop="confirmPassword">
        <el-input placeholder="再次输入密码" v-model="user.confirmPassword" type="password"/>
      </el-form-item>
      <el-form-item label='姓名' prop="realName">
        <el-input placeholder="请输入姓名" v-model="user.realName"/>
      </el-form-item>
      <el-form-item label='学号' prop="workId">
        <el-input placeholder="请输入学号" v-model="user.workId"/>
      </el-form-item>
      <el-form-item label='所属学院' prop="institute">
        <el-input placeholder="请输入所属学院" v-model="user.institute"/>
      </el-form-item>
      <el-form-item label='电话' prop="telPhone">
        <el-input placeholder="请输入电话" v-model="user.telPhone"/>
      </el-form-item>
      <el-checkbox v-model="checked">是否为超级管理员</el-checkbox>
      <el-form-item style="position: relative; right: 30px; margin-top: 20px">
        <el-button type='primary' @click="doRegister">确认添加</el-button>
        <el-button type='danger' @click="reset('user')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
  export default {
    name: "AdminRegister",
    data() {
      const validateNull = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('此为必填项'));
        } else {
          callback()
        }
      }
      const validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.user.password !== '') {
            this.$refs.user.validateField('confirmPassword');
          }
          callback();
        }
      };
      const confirmPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.user.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        loading: false,
        checked: false,
        rules: {
          username: [{validator: validateNull, trigger: 'blur'}],
          password: [{validator: validatePass, trigger: 'blur'}],
          confirmPassword: [{validator: confirmPassword, trigger: 'blur'}],
          realName: [{validator: validateNull, trigger: 'blur'}],
          workId: [{validator: validateNull, trigger: 'blur'}],
          institute: [{validator: validateNull, trigger: 'blur'}],
          telPhone: [{validator: validateNull, trigger: 'blur'}]
        },
        errorMsg: "",
        user: {
          username: "",
          password: "",
          confirmPassword: "",
          workId: "",
          realName: "",
          telPhone: "",
          institute: "",
          authorities: ["USER", "ADMIN"]
        }
      }
    },
    methods: {
      doRegister() {
        this.loading = true
        if (this.checked) {
          this.user.authorities.push("SUPER_ADMIN")
        }
        this.$axios.post("/users/addAdminUser", this.user)
          .then(res => {
            let data = res.data;
            console.log(data)
            if (data === true) {
              this.$message({
                showClose: true,
                message: "添加成功",
                type: 'success'
              })
              this.loading = false
            }
            if (data === false) {
              this.$message({
                showClose: true,
                message: "添加失败，请稍后再试",
                type: 'error'
              })
            }
            if (data["httpCode"] === "400") {
              this.$message({
                showClose: true,
                message: data["message"],
                type: 'error'
              })
            }
            this.loading = false
          })
          .catch(Error => {
            this.$message({
              showClose: true,
              message: "服务不可用",
              type: 'error'
            })
            this.loading = false
          });
      },
      reset(user) {
        this.$refs[user].resetFields();
        this.checked = false
      }
    }
  }
</script>

<style>
  .admin-register-container {
    position: relative;
    top: -80px;
    left: -120px;
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 500px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .register_title {
    margin: 0 auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
