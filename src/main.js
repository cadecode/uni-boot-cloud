import Vue from 'vue'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// A modern alternative to CSS resets
import 'normalize.css/normalize.css'
// global css
import '@/style/index.scss'

import App from './App'
import store from './store'
import router from './router'

// icon
import '@/icon'
// 权限控制
import '@/util/permission'

// 使用ElementUI
Vue.use(ElementUI)

// 开启生产提示，正式环境可只为false以减少warn
Vue.config.productionTip = true

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
