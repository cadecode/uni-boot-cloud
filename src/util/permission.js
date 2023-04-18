import router from '../router'
import store from '../store'
// NProgress and style
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

import { setPageTitle } from '@/util/pageTitle'
import { getToken } from '@/util/auth'

// NProgress config
NProgress.configure({ showSpinner: false })

router.beforeEach(async(to, from, next) => {
  console.log(from, to)
  NProgress.start()
  setPageTitle(to.meta.title)
  // 从cookie获取，刷新后不丢失
  const hasToken = getToken()
  // 未登录
  if (!hasToken) {
    // 未登录时放通login
    if (to.path === '/login') {
      next()
      return
    }
    // 跳转login
    next(`/login?redirect=${to.path}`)
    NProgress.done()
    return
  }
  const routes = store.getters.routes
  // 若没有设置routes
  if (routes && routes.length === 0) {
    // 获取menuList
    const menuList = await store.dispatch('user/getInfo')
    // 生成路由
    const asyncRoutes = await store.dispatch('permission/generateRoutes', menuList)
    router.addRoutes(asyncRoutes)
    next({ ...to, replace: true })
    return
  }
  next()
})

router.afterEach(() => {
  NProgress.done()
})
