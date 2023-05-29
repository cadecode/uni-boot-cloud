<template>
  <div :class="{'has-logo':showLogo}">
    <app-sidebar-logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="false"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="false"
        mode="vertical"
      >
        <app-sidebar-menu v-for="route in routes" :key="route.path" :item="route" :base-path="route.path" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>
<script>
import variables from '@/style/variables.scss';
import {mapGetters} from 'vuex';
import AppSidebarMenu from '@/layout/component/AppSidebar/AppSidebarMenu.vue';
import AppSidebarLogo from '@/layout/component/AppSidebar/AppSidebaeLogo.vue';

export default {
  name: 'AppSidebar',
  components: {AppSidebarLogo, AppSidebarMenu},
  computed: {
    ...mapGetters([
      'routes',
      'sidebar'
    ]),
    activeMenu() {
      const route = this.$route;
      const {meta, path} = route;
      // 当路由设置了该属性，则会高亮相对应的侧边栏
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    showLogo() {
      return this.$store.state.app.sidebarLogoFlag;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    }
  }
};
</script>
