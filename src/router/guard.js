import NProgress from 'nprogress';
import store from '@/store';
import {getPageTitle} from '@/util/common';
import {getToken} from '@/util/cookie';

/**
 * 设置router的全局路由守卫
 */
function setGuard(router) {
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
      next(`/login?redirect=${to.fullPath}`);
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
      // 加载路由
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
}

export {
  setGuard
};
