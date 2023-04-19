/**
 * 菜单路由转换工具
 */
import Layout from '@/layout';
import {Message} from 'element-ui';
import NProgress from 'nprogress';

/**
 * 是否是外部链接
 */
function isExternalUrl(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

/**
 * 后端menu转为路由
 * @param menu
 * @param menu.parentId 父级菜单id
 * @param menu.leafFlag 是否是页面
 * @param menu.routePath 路由路径
 * @param menu.routeName 路由名称
 * @param menu.componentPath 组件路径
 * @param menu.menuName 菜单名称
 * @param menu.icon 图标
 */
function menuToRoute(menu) {
  // 顶级且是页面
  if (menu.parentId === null && menu.leafFlag) {
    const route = {
      path: menu.routePath,
      component: Layout,
      children: [
        {
          path: '',
          name: menu.routeName,
          component: requireComponent(menu.componentPath),
          meta: {title: menu.menuName, icon: menu.icon}
        }
      ],
      meta: {}
    };
    return {route, currRoute: null};
  }
  // 顶级且是菜单
  if (menu.parentId === null && !menu.leafFlag) {
    const route = {
      alwaysShow: true,
      redirect: 'noRedirect',
      path: menu.routePath,
      name: menu.routeName,
      component: Layout,
      meta: {title: menu.menuName, icon: menu.icon}
    };
    return {route, currRoute: route};
  }
  // 子级
  if (menu.parentId !== null) {
    const route = {
      path: menu.routePath,
      name: menu.routeName,
      component: requireComponent(menu.componentPath),
      meta: {title: menu.menuName, icon: menu.icon}
    };
    return {route, currRoute: route};
  }
}

/**
 * 使用require加载组件，失败时弹出提示
 */
function requireComponent(path) {
  return (resolve) => {
    return require([`@/view${path}`], resolve)
      .catch(e => {
        Message({
          message: `加载组件失败，@/view${path}`,
          type: 'error',
          duration: 5 * 1000,
          dangerouslyUseHTMLString: true
        });
        NProgress.done();
        throw e;
      });
  };
}

/**
 * 转换后端menu列表为routes
 * 后端menu结构：{id,parentId,routeName,routePath,componentPath,menuName,leafFlag,icon}
 */
function convertAsyncRoutes(menuList) {
  const routes = [];
  for (let i = 0; i < menuList.length; i++) {
    const menu = menuList[i];
    const menuRoute = menuToRoute(menu);
    routes.push(menuRoute.route);
    if (menuRoute.currRoute && menu.children) {
      menuRoute.currRoute.children = convertAsyncRoutes(menu.children);
    }
  }
  return routes;
}

export {
  isExternalUrl,
  convertAsyncRoutes
};
