/**
 * store user模块
 * 管理登录信息、用户信息、路由表
 */
import {getInfo, login, logout} from '@/api/login';
import {getToken, removeToken, setToken} from '@/util/token';
import {constRoutes, notFoundRoute, resetRouter} from '@/router';
import {convertAsyncRoutes} from '@/util/menu';

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    // login接口后端对象
    userInfo: {},
    // 全部路由
    routes: [],
    // 新添路由
    addRoutes: []
  };
};

const state = getDefaultState();

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState());
  },
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo;
  },
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes;
    state.routes = constRoutes.concat(routes);
  }
};

const actions = {
  // 登录
  login({commit}, userInfo) {
    const formData = new FormData();
    Object.keys(userInfo).forEach(k => formData.append(k, userInfo[k]));
    return login(formData).then(res => {
      const {data} = res;
      const {nickName, roles} = data;
      commit('SET_USER_INFO', data);
      commit('SET_NAME', nickName);
      commit('SET_ROLES', roles);
    });
  },
  // 获取用户信息
  getInfo() {
    return getInfo().then(async(res) => {
      const {menuList} = res.data;
      return menuList;
    });
  },
  // 注销
  logout({commit}) {
    return logout().then(() => {
      // 清理cookie token
      removeToken();
      // 清理路由
      resetRouter();
      commit('RESET_STATE');
    });
  },
  // 设置token
  setToken({commit}, token) {
    commit('SET_TOKEN', token);
    setToken(token);
  },
  // 重置token
  resetToken({commit}) {
    return new Promise(resolve => {
      removeToken();
      commit('RESET_STATE');
      resolve();
    });
  },
  generateRoutes({commit}, menuList) {
    return new Promise(resolve => {
      const asyncRoutes = convertAsyncRoutes(menuList) || [];
      // 添加404兜底路由
      asyncRoutes.push(notFoundRoute);
      commit('SET_ROUTES', asyncRoutes);
      resolve(asyncRoutes);
    });
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
};

