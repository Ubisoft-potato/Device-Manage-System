<template>
  <el-menu
    :default-active="'/home'"
    router
    mode="horizontal"
    background-color="white"
    text-color="#222"
    active-text-color="#409EFF"
    style="min-width: 1300px; position: relative;top: -55px">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      {{ item.navItem }}
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
        navList: [
          {name: '/home', navItem: '首页'},
          {name: '/admin', navItem: '个人中心'}
        ]
      }
    }
  }
</script>

<style scoped>
  a {
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }
</style>
