<template>
  <div>
    <nav-menu/>
    <router-view/>
  </div>
</template>

<script>
  import NavMenu from './common/NavMenu'

  export default {
    name: "AppIndex",
    components: {NavMenu}
  }
</script>

<style>

</style>
