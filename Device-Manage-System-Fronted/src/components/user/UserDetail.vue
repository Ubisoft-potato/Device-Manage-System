<template>
  <div>
    <el-card>
      {{userInfo.id}}
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "UserDetail",
    props: {
      userInfo: Object
    }
  }
</script>

<style>

</style>
