<template>
  <el-container class="UserManage-container">
    <el-aside>
      <el-menu
        class="el-menu-vertical-demo">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-user-solid"/>
            <span>用户浏览</span>
          </template>
          <el-menu-item index="1-1" @click="getOrdinaryUser">
            <i class="el-icon-user"/>
            <span slot="title">普通用户</span>
          </el-menu-item>
          <el-menu-item index="1-2" @click="getAdminUser">
            <i class="el-icon-s-check"/>
            <span slot="title">管理员用户</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item index="3" @click="addAdmin">
          <i class="el-icon-circle-plus-outline"/>
          <span slot="title">添加管理员</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <user-table :authority="authority" v-if="showUserTable"/>
      <AdminRegister v-if="showAdminRegister"/>
    </el-main>
  </el-container>
</template>

<script>
  import UserTable from "./UserTable";
  import AdminRegister from "./AdminRegister";

  export default {
    name: "UserManage",
    components: {UserTable, AdminRegister},
    data() {
      return {
        showUserTable: false,
        showAdminRegister: false,
        authority: ""
      }
    },
    methods: {
      getOrdinaryUser() {
        this.$nextTick(() => {
          this.showAdminRegister = false
          this.showUserTable = true
        });
        this.authority = "USER"
      },
      getAdminUser() {

        this.$nextTick(() => {
          this.showAdminRegister = false
          this.showUserTable = true
        });
        this.authority = "ADMIN"
      },
      addAdmin() {
        this.$nextTick(() => {
          this.showUserTable = false
          this.showAdminRegister = true
        });
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
