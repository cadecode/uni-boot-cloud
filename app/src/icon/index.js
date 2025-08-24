import Vue from 'vue';
// svg component
import BaseSvgIcon from '@/component/BaseSvgIcon';

// register globally
Vue.component('base-svg-icon', BaseSvgIcon);

const req = require.context('./svg', false, /\.svg$/);
const requireAll = requireContext => requireContext.keys().map(requireContext);
requireAll(req);
