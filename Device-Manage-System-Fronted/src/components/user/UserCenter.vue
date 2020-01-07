<template>
  <el-container>
    <el-aside>
      <el-menu
        class="el-menu-vertical-demo">
        <el-menu-item index="1" @click="getUserInfo">
          <i class="el-icon-user"/>
          <span slot="title">修改信息</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-edit"/>
          <span slot="title">修改密码</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <UserDetail :userInfo="user" v-if="showDetail"/>
    </el-main>
  </el-container>
</template>

<script>
  import UserDetail from "./UserDetail";

  export default {
    name: "UserCenter",
    components: {
      UserDetail
    },
    data() {
      return {
        showDetail: false,
        user: {}
      }
    },
    methods: {
      getUserInfo() {
        this.showDetail = true
        this.$axios.get("/users/info")
          .then(res => {
            console.log(res.data)
            this.user = res.data
          }).catch(Error => {
          // this.$message({
          //   showClose: true,
          //   message: "获取用户信息失败,请重新登录",
          //   type: 'error'
          // })
          // window.localStorage.clear()
          // this.$router.push("/login")
        })
      }
    }
  }
</script>

<style>

</style>
