import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

/**
 * A modern alternative to CSS resets
 */
import 'normalize.css/normalize.css';

/**
 * global css
 */
import '@/style/index.scss';

/**
 * icon
 */
import '@/icon';

/**
 * vue入口组件
 */
import App from '@/App';

/**
 * 引入vuex及配置
 */
import store from '@/store';

/**
 * 引入vue-router及配置
 */
import router from '@/router';

/**
 * 使用ElementUI
 */
Vue.use(ElementUI);

/**
 * 开启生产提示，正式环境可只为false以减少warn
 */
Vue.config.productionTip = true;

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
});
