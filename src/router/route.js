import {Message} from 'element-ui';
import NProgress from 'nprogress';
import Layout from '@/layout';

/**
 * 固定路由
 */
const constRoutes = [
  {
    path: '/login',
    component: () => import('@/view/Login'),
    // hidden 表示不在侧边菜单展示
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/view/Page404.vue'),
    hidden: true
  }
];

/**
 * home路由，需要修改redirect使用
 */
const homeRoute = {path: '/', hidden: true};

/**
 * 404兜底路由
 */
const notFoundRoute = {path: '*', redirect: '/404', hidden: true};

/**
 * @typedef {Object} Menu
 * @property {number} parentId 父级菜单id
 * @property {boolean} leafFlag 是否是页面
 * @property {boolean} hiddenFlag 是否是
 * @property {string} routePath 路由路径
 * @property {string} routeName 路由名称
 * @property {string} componentPath 组件路径
 * @property {string} menuName 菜单名称
 * @property {string} icon 图标
 * @property {string} title 标题
 * @property {string} icon 图标
 * @property {string} cacheFlag 是否缓存
 * @property {string} affix 是否钉在 TAG 栏（暂未实现）
 */

/**
 * 后端menu转为路由
 * @param {Menu} menu
 * @return {Object} {route, currRote}
 */
function menuToRoute(menu) {
  // 顶级且是页面
  if (menu.parentId === null && menu.leafFlag) {
    const route = {
      path: menu.routePath,
      component: Layout,
      hidden: menu.hiddenFlag,
      children: [
        {
          path: '',
          name: menu.routeName,
          component: requireComponent(menu.componentPath),
          meta: {title: menu.menuName, icon: menu.icon, cacheFlag: menu.cacheFlag}
        }
      ],
      meta: {}
    };
    return {route, currRoute: null};
  }
  // 顶级且是菜单
  if (menu.parentId === null && !menu.leafFlag) {
    const route = {
      // alwaysShow 不管多少 children，都会展示展开箭头
      alwaysShow: true,
      path: menu.routePath,
      name: menu.routeName,
      component: Layout,
      hidden: menu.hiddenFlag,
      // noRedirect 面包屑中是否可点击导航
      meta: {title: menu.menuName, icon: menu.icon, noRedirect: true, cacheFlag: menu.cacheFlag}
    };
    return {route, currRoute: route};
  }
  // 子级
  if (menu.parentId !== null) {
    const route = {
      alwaysShow: !menu.leafFlag,
      path: menu.routePath,
      name: menu.routeName,
      component: requireComponent(menu.componentPath),
      hidden: menu.hiddenFlag,
      meta: {title: menu.menuName, icon: menu.icon, cacheFlag: menu.cacheFlag}
    };
    return {route, currRoute: route};
  }
}

/**
 * 使用require加载组件，失败时弹出提示
 * @param {string} path 组件路径
 * @return {function} 加载组件的方法
 */
function requireComponent(path) {
  return (resolve) => {
    return require([`@/view${path}`], resolve)
      .catch(e => {
        Message.error({message: `加载组件失败，@/view${path}`, duration: 5 * 1000});
        NProgress.done();
        throw e;
      });
  };
}

/**
 * 转换后端menu列表为routes
 * 后端menu结构：{id,parentId,routeName,routePath,componentPath,menuName,leafFlag,icon}
 * @param {Menu[]} menuList
 * @return {Array} 路由数组
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
  constRoutes,
  homeRoute,
  notFoundRoute,
  convertAsyncRoutes
};
