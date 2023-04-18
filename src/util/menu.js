/**
 * 菜单路由转换工具
 */
import Layout from '@/layout';

/**
 * 是否是外部链接
 * @param {string} path
 * @returns {Boolean}
 */
function isExternalUrl(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

/**
 * 转换后端menu列表为routes
 * 后端menu结构：{id,parentId,routeName,routePath,componentPath,menuName,leafFlag,icon}
 * @param menuList
 * @returns {*}
 */
function convertAsyncRoutes(menuList) {
  let hasHomeFlag = false;
  return menuList.map(m => {
    // 顶级且是页面，特殊处理，兼容vue-admin-template风格
    if (m.parentId === null && m.leafFlag) {
      // 第一个作为首页
      if (!hasHomeFlag) {
        hasHomeFlag = true;
        return {
          path: '/',
          redirect: m.routePath,
          component: Layout,
          children: [
            {
              path: m.routePath,
              name: m.routeName,
              component: (resolve) => require([`@/view${m.componentPath}`], resolve),
              meta: {title: m.menuName, icon: m.icon, homeFlag: true}
            }
          ]
        };
      }
      return {
        path: m.routePath,
        component: Layout,
        children: [
          {
            path: '',
            name: m.routeName,
            component: (resolve) => require([`@/view${m.componentPath}`], resolve),
            meta: {title: m.menuName, icon: m.icon}
          }
        ]
      };
    }
    let route;
    // 顶级且是菜单
    if (m.parentId === null && !m.leafFlag) {
      route = {
        path: m.routePath,
        name: m.routeName,
        component: Layout,
        meta: {title: m.menuName, icon: m.icon}
      };
    } else if (m.parentId !== null) {
      route = {
        path: m.routePath,
        name: m.routeName,
        component: (resolve) => require([`@/view${m.componentPath}`], resolve),
        meta: {title: m.menuName, icon: m.icon}
      };
    }
    if (m.children) {
      route.children = convertAsyncRoutes(m.children);
    }
    return route;
  });
}

export {
  isExternalUrl,
  convertAsyncRoutes
};
