<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
        <router-view :key="key" />
      </keep-alive>
    </transition>
  </section>
</template>
<script>
export default {
  name: 'AppMain',
  computed: {
    key() {
      return this.$route.path;
    },
    cachedViews() {
      return this.$store.state.view.cachedViews;
    }
  },
  watch: {
    $route() {
      this.addCurrView();
    }
  },
  mounted() {
    this.addCurrView();
  },
  methods: {
    addCurrView() {
      const {name} = this.$route;
      if (name) {
        this.$store.dispatch('view/addView', this.$route);
      }
      return false;
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
