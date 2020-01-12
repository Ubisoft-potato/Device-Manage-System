<template>
  <el-menu
    :default-active="'/home'"
    router
    mode="horizontal"
    background-color="white"
    text-color="#222"
    active-text-color="#409EFF"
    style="min-width: 1300px; position: relative;top: -55px; font-family: Microsoft YaHei,monospace">
    <div style="float: left; position: relative; top: 13px">
      <el-image
        src="static/cust.png"
        fit="contain"/>
      <el-image
        src="static/cust-font.png"
        fit="contain"/>
    </div>
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      {{ item.navItem }}
    </el-menu-item>
    <el-submenu style="float: right" index="user">
      <template slot="title">
        <span class="el-icon-user"/>
        {{user.realName}}
      </template>
      <el-menu-item index="" style="text-align: center; color: #303133" disabled>{{user.institute}}</el-menu-item>
      <el-menu-item index="" style="text-align: center; color: #F56C6C" @click="logout">退出登录</el-menu-item>
    </el-submenu>
    <el-menu-item v-show="user.isSuperAdmin" style="float: right" index="/userManage">
      用户管理
    </el-menu-item>
    <el-menu-item v-show="user.authorities.includes('ADMIN')" style="float: right" index="/deviceManage">
      设备管理
    </el-menu-item>
    <span
      style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold">高校大型实验仪器设备共享管理平台
    </span>

  </el-menu>
</template>

<script>
  export default {
    name: 'NavMenu',
    data() {
      return {
        user: {},
        navList: [
          {name: '/home', navItem: '首页'},
          {name: '/reservationHistory', navItem: '设备预约历史'},
          {name: '/userCenter', navItem: '个人中心'}
        ],
      }
    },
    created() {
      this.$axios.get("/users/info")
        .then(res => {
          this.user = res.data
        }).catch(Error => {
        this.$message({
          showClose: true,
          message: "获取用户信息失败",
          type: 'error'
        })
      })
    },

    methods: {
      logout() {
        this.$axios.get("/logout")
          .then(res => {
            let data = res.data;
            if (data["httpCode"] === "200") {
              this.$message({
                showClose: true,
                message: data["message"],
                type: "success"
              })
            }
            window.localStorage.clear()
            this.$router.push("/login")
          })
          .catch(error => {

          })

      }
    }
  }
</script>

<style>
  a {
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }


</style>
