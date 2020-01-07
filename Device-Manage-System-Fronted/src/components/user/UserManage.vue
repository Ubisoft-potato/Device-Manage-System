<template>
  <el-container class="UserManage-container">
    <el-aside>
      <el-menu
        class="el-menu-vertical-demo">
        <el-menu-item index="1" @click="getOrdinaryUser">
          <i class="el-icon-user"/>
          <span slot="title">普通用户</span>
        </el-menu-item>
        <el-menu-item index="2" @click="getAdminUser">
          <i class="el-icon-s-check"/>
          <span slot="title">管理员</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <user-table :authority="authority"/>
    </el-main>
  </el-container>
</template>

<script>
  import UserTable from "./UserTable";

  export default {
    name: "UserManage",
    components: {UserTable},
    data() {
      return {
        authority: ""
      }
    },
    methods: {
      getOrdinaryUser() {
        this.authority = "USER"
      },
      getAdminUser() {
        this.authority = "ADMIN"
      }
    }
  }
</script>

<style>
  .UserManage-container {
    position: relative;
    top: -55px;
  }
</style>
