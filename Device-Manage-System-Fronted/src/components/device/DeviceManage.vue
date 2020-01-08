<template>
  <el-container class="DeviceManage-container">
    <el-aside>
      <el-menu
        default-active="'/deviceTable'"
        router
        class="el-menu-vertical-demo">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-s-operation"/>
            <span>设备综合概览</span>
          </template>
          <el-menu-item index="1-1" :index="'/deviceTable'">
            <i class="el-icon-search"/>设备查询
          </el-menu-item>
          <el-menu-item index="1-2" :index="'/addDevice'">
            <i class="el-icon-circle-plus-outline"/>增加设备
          </el-menu-item>

        </el-submenu>
        <el-menu-item :index="'/deviceCheck'">
          <i class="el-icon-s-check"/>
          <span slot="title">设备预约审核</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "DeviceManage"
  }
</script>

<style>
  .DeviceManage-container {
    position: relative;
    top: -55px;
  }
</style>
