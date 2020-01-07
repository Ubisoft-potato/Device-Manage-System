<template>
  <div>
    <el-card class="userInfo-container" v-loading="loading" element-loading-text="修改中...">
      <h3 class="register_title">信息修改</h3>
      <el-form status-icon label-width=auto :model="userInfo" ref="user" :rules="rules">
        <el-form-item label='账号' prop="username">
          <el-input placeholder="请输入账号" disabled v-model="userInfo.username"/>
        </el-form-item>
        <el-form-item label='姓名' prop="realName">
          <el-input placeholder="请输入姓名" v-model="userInfo.realName"/>
        </el-form-item>
        <el-form-item label='学号' prop="workId">
          <el-input placeholder="请输入学号" v-model="userInfo.workId"/>
        </el-form-item>
        <el-form-item label='所属学院' prop="institute">
          <el-input placeholder="请输入所属学院" v-model="userInfo.institute"/>
        </el-form-item>
        <el-form-item label='电话' prop="telPhone">
          <el-input placeholder="请输入电话" v-model="userInfo.telPhone"/>
        </el-form-item>
        <el-form-item style="position: relative; right: 30px">
          <el-button type='primary' @click="update">确认修改</el-button>
          <el-button type='danger' @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "UserDetail",
    props: {
      user: Object
    },
    data() {
      const validateNull = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('此为必填项'));
        } else {
          callback()
        }
      }
      return {
        loading: false,
        userInfo: {},
        rules: {
          username: [{validator: validateNull, trigger: 'blur'}],
          realName: [{validator: validateNull, trigger: 'blur'}],
          workId: [{validator: validateNull, trigger: 'blur'}],
          institute: [{validator: validateNull, trigger: 'blur'}],
          telPhone: [{validator: validateNull, trigger: 'blur'}]
        }
      }
    },
    watch: {
      user: function () {
        this.userInfo = this.user
      }
    },
    methods: {
      update() {
        this.loading = true
        console.log(this.userInfo.id)
        this.$axios.put("/users/update", this.userInfo)
          .then(res => {
            console.log(res.data)
            if (res.data) {
              this.$message({
                showClose: true,
                message: "更新成功",
                type: "success"
              })
            } else {
              this.$message({
                showClose: true,
                message: "更新失败，请稍后再试",
                type: "error"
              })
            }
            this.loading = false
          })
          .catch(error => {
            this.$message({
              showClose: true,
              message: "更新失败，请稍后再试",
              type: "error"
            })
            this.loading = false
          })
      },
      reset() {
        this.$axios.get("/users/info")
          .then(res => {
            this.userInfo = res.data
          })
          .catch(error => {

          })
      }
    }
  }
</script>

<style>
  .userInfo-container {
    position: relative;
    top: -50px;
    left: -120px;
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 550px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
</style>
