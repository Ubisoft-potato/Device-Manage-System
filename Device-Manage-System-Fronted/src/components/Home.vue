<template>
  <div>
    <el-container class="home-container">
      <el-aside>
        <el-menu router>
          <el-menu-item :index="'/'">
            <i class="el-icon-chat-line-square"/>
            <span slot="title">通知</span>
          </el-menu-item>
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
