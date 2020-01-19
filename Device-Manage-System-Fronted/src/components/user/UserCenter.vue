<template>
  <el-container class="container">
    <el-aside>
      <el-menu
        class="el-menu-vertical-demo">
        <el-menu-item index="1" @click="getUserInfo">
          <i class="el-icon-user"/>
          <span slot="title">修改信息</span>
        </el-menu-item>
        <el-menu-item index="2" @click="changPassword">
          <i class="el-icon-edit"/>
          <span slot="title">修改密码</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <UserDetail :user="user" v-if="showDetail"/>
      <router-view/>
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
        showResetPage: false,
        user: {}
      }
    },
    methods: {
      getUserInfo() {
        this.$router.push("/userCenter")
        this.showDetail = true
        this.$axios.get("/users/info")
          .then(res => {
            console.log(res.data)
            this.user = res.data
          }).catch(Error => {

        })
      },
      changPassword() {
        this.showDetail = false
        this.$router.push("/resetPassword")
      }
    }
  }
</script>

<style>
  .container {
    position: relative;
    top: -55px;
  }
</style>
