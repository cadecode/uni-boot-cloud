<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
  </section>
</template>
<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path;
    }
  }
};
</script>
<style scoped>
.app-main {
  min-height: 100vh;
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header + .app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// 没有出现滚动条时，导致头像位置横向跳动
// fix css style bug in open el-dialog
/*.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}*/
</style>
