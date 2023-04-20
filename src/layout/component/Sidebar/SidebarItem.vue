<template>
  <div v-if="!item.hidden">
    <template v-if="hasOneShowingChild(item.children, item)">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{'submenu-title-noDropdown':!isNest}">
          <item
            :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)"
            :title="onlyOneChild.meta.title"
            :is-external="isExternalUrl(onlyOneChild.path)"
          />
        </el-menu-item>
      </app-link>
    </template>

    <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template slot="title">
        <item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        class="nest-menu"
      />
    </el-submenu>
  </div>
</template>

<script>
import path from 'path';
import {isExternalUrl} from '@/util/menu';
import Item from './Item.vue';
import AppLink from './Link.vue';
import FixIOSBug from '@/layout/mixin/FixIOSBug';

export default {
  name: 'SidebarItem',
  components: {Item, AppLink},
  mixins: [FixIOSBug],
  props: {
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    // fix:https://github.com/PanJiaChen/vue-admin-template/issues/237
    this.onlyOneChild = null;
    return {
      // onlyOneChild: null
    };
  },
  methods: {
    isExternalUrl(path) {
      return isExternalUrl(path) || isExternalUrl(this.basePath);
    },
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false;
        } else {
          this.onlyOneChild = item;
          return true;
        }
      });
      // 没有子路由时显示父级
      if (showingChildren.length === 0) {
        // 父级属性放入onlyOneChild
        this.onlyOneChild = {...parent, path: '', noShowingChildren: true};
        // alwaysShow将显示为菜单，非alwaysShow并且没有子路由则显示成页面（表现为不带展开图标）
        return !parent.alwaysShow;
      }
      // 当只有一个子路由器，默认显示子路由
      if (showingChildren.length === 1) {
        // onlyOneChild没有children并且父级不是alwaysShow
        return !this.onlyOneChild.children && !parent.alwaysShow;
      }
      return false;
    },
    resolvePath(routePath) {
      if (isExternalUrl(routePath)) {
        return routePath;
      }
      if (isExternalUrl(this.basePath)) {
        return this.basePath;
      }
      return path.resolve(this.basePath, routePath);
    }
  }
};
</script>
