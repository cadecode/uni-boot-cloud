/**
 * 当路由children声明的路由大于等于1个时，子菜单才会显示
 * Detail: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 */
import Vue from 'vue';
import Router from 'vue-router';
import NProgress from 'nprogress';
import store from '@/store';
import {getPageTitle} from '@/util/common';
import {getToken} from '@/util/token';

Vue.use(Router);

// NProgress config
NProgress.configure({showSpinner: false});

/**
 * 固定路由
 */
const constRoutes = [
  {
    path: '/login',
    component: () => import('@/view/Login'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/view/404'),
    hidden: true
  }
];

/**
 * 404兜底路由
 */
const notFoundRoute = {path: '*', redirect: '/404', hidden: true};

/**
 * 创建router
 */
const createRouter = () => new Router({
  // mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: constRoutes
});
const router = createRouter();

/**
 * 重置router，重置matcher以清除旧的路由匹配
 * detail:https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
 */
function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
}

// 全局路由守卫
router.beforeEach(async(to, from, next) => {
  NProgress.start();
  document.title = getPageTitle(to.meta.title);
  // 从cookie获取，刷新后不丢失
  const hasToken = getToken();
  // 未登录
  if (!hasToken) {
    // 未登录时放通login
    if (to.path === '/login') {
      next();
      return;
    }
    // 跳转login
    next(`/login?redirect=${to.path}`);
    NProgress.done();
    return;
  }
  const routes = store.getters.routes;
  // 若没有设置routes
  if (routes && routes.length === 0) {
    // 获取menuList
    const menuList = await store.dispatch('user/getInfo');
    // 生成路由
    const asyncRoutes = await store.dispatch('user/generateRoutes', menuList);
    router.addRoutes(asyncRoutes);
    next({...to, replace: true});
    NProgress.done();
    return;
  }
  next();
});

router.afterEach(() => {
  NProgress.done();
});

export {
  router as default,
  constRoutes,
  notFoundRoute,
  resetRouter
};
