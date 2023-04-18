/**
 * store app模块
 * 管理应用配置与设定相关
 */
import Cookies from 'js-cookie';
import settings from '@/settings';

const {fixedHeaderFlag, sidebarLogoFlag} = settings;

const state = {
  sidebar: {
    opened: Cookies.get('sidebarStatus') ? Boolean(Cookies.get('sidebarStatus')) : true,
    withoutAnimation: false
  },
  device: 'desktop',
  fixedHeaderFlag: fixedHeaderFlag,
  sidebarLogoFlag: sidebarLogoFlag
};

const mutations = {
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened;
    state.sidebar.withoutAnimation = false;
    if (state.sidebar.opened) {
      Cookies.set('sidebarStatus', 1);
    } else {
      Cookies.set('sidebarStatus', 0);
    }
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 0);
    state.sidebar.opened = false;
    state.sidebar.withoutAnimation = withoutAnimation;
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device;
  },
  CHANGE_SETTING: (state, {key, value}) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value;
    }
  }
};

const actions = {
  toggleSideBar({commit}) {
    commit('TOGGLE_SIDEBAR');
  },
  closeSideBar({commit}, {withoutAnimation}) {
    commit('CLOSE_SIDEBAR', withoutAnimation);
  },
  toggleDevice({commit}, device) {
    commit('TOGGLE_DEVICE', device);
  },
  changeSetting({commit}, data) {
    commit('CHANGE_SETTING', data);
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
};
