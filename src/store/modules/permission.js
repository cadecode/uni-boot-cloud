import { asyncRoutes, constantRoutes } from '@/router'
import Layout from '@/layout/index'

/**
 * 从后端menuList构建asyncRoutes
 */
function convertAsyncRoutes(menuList) {
  let hasHomeFlag = false
  return menuList.map(m => {
    // 顶级且是页面，特殊处理，兼容vue-admin-template风格
    if (m.parentId === null && m.leafFlag) {
      // 第一个作为首页
      if (!hasHomeFlag) {
        hasHomeFlag = true
        return {
          path: '/',
          redirect: m.routePath,
          component: Layout,
          children: [
            {
              path: m.routePath,
              name: m.routeName,
              component: (resolve) => require([`@/views${m.routePath}/index`], resolve),
              meta: { title: m.menuName, icon: m.icon }
            }
          ]
        }
      }
      return {
        path: m.routePath,
        component: Layout,
        children: [
          {
            path: 'index',
            name: m.routeName,
            component: (resolve) => require([`@/views${m.routePath}/index`], resolve),
            meta: { title: m.menuName, icon: m.icon }
          }
        ]
      }
    }
    let route
    // 顶级且是菜单
    if (m.parentId === null && !m.leafFlag) {
      route = {
        path: m.routePath,
        name: m.routeName,
        component: Layout,
        meta: { title: m.menuName, icon: m.icon }
      }
    }
    // 子级
    if (m.parentId !== null) {
      route = {
        path: m.routePath,
        name: m.routeName,
        component: (resolve) => require([`@/views${m.routePath}/index`], resolve),
        meta: { title: m.menuName, icon: m.icon }
      }
    }
    if (m.children) {
      route.children = convertAsyncRoutes(m.children)
    }
    return route
  })
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, menuList) {
    return new Promise(resolve => {
      const menuRoutes = convertAsyncRoutes(menuList) || []
      const routes = menuRoutes.concat(asyncRoutes)
      commit('SET_ROUTES', routes)
      resolve(routes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
