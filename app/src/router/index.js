/**
 * 当路由children声明的路由大于等于1个时，子菜单才会显示
 * Detail: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 */
import Vue from 'vue';
import Router from 'vue-router';
import NProgress from 'nprogress';
import {constRoutes} from '@/router/route';
import {setGuard} from '@/router/guard';
// NProgress config
NProgress.configure({showSpinner: false});

Vue.use(Router);

/**
 * 创建router
 */
const createRouter = () => new Router({
  // mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: constRoutes
});

/**
 * 重置router，重置matcher以清除旧的路由匹配
 * detail:https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
 */
function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
}

const router = createRouter();

setGuard(router);

export {
  router as default,
  resetRouter
};
