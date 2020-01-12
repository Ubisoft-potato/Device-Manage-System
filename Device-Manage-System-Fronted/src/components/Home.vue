<template>
  <div>
    <el-container class="home-container">
      <el-aside>
        <el-menu router>
          <el-menu-item :index="'/'">
            <i class="el-icon-chat-line-square"/>
            <span slot="title">通知</span>
          </el-menu-item>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-platform"/>
              <span>所有设备</span>
            </template>
            <el-menu-item index="1-1" :index="'/deviceSearch'">
              <i class="el-icon-search"/>搜索设备
            </el-menu-item>
            <el-menu-item index="1-2" :index="'/publicTable'">
              <i class="el-icon-view"/>浏览设备
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: "Home"
  }
</script>

<style>
  .home-container {
    position: relative;
    top: -55px;
  }
</style>
