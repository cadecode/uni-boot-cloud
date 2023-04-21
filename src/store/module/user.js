/**
 * store user模块
 * 管理登录信息、用户信息、路由表
 */
import {getInfo, login, logout} from '@/api/login';
import {
  getAvatar,
  getName,
  getRoles,
  getToken,
  getUserInfo,
  removeToken,
  setAvatar,
  setName,
  setRoles,
  setToken,
  setUserInfo
} from '@/util/cookie';
import {resetRouter} from '@/router';
import {constRoutes, convertAsyncRoutes, homeRoute, notFoundRoute} from '@/router/route';

const getDefaultState = () => {
  return {
    token: getToken(),
    name: getName(),
    avatar: getAvatar(),
    roles: getRoles(),
    // login接口后端对象
    userInfo: getUserInfo(),
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
    setToken(token);
  },
  SET_NAME: (state, name) => {
    state.name = name;
    setName(name);
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
    setAvatar(avatar);
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
    setRoles(roles);
  },
  SET_USER_INFO: (state, userInfo) => {
    state.userInfo = userInfo;
    setUserInfo(userInfo);
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
      const {nickName, roles, avatar} = data;
      commit('SET_USER_INFO', data);
      commit('SET_NAME', nickName);
      commit('SET_AVATAR', avatar);
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
  logout() {
    return logout();
  },
  // 设置token
  setToken({commit}, token) {
    return new Promise(resolve => {
      commit('SET_TOKEN', token);
      resolve();
    });
  },
  // 重置token
  resetToken({commit}) {
    return new Promise(resolve => {
      // 清理cookie token
      removeToken();
      // 重置路由
      resetRouter();
      // 重置state
      commit('RESET_STATE');
      resolve();
    });
  },
  // 生成路由表
  generateRoutes({commit}, menuList) {
    return new Promise(resolve => {
      const asyncRoutes = convertAsyncRoutes(menuList) || [];
      // 第一条作为home路由
      if (asyncRoutes.length > 0) {
        // 设置home路由重定向
        homeRoute.redirect = asyncRoutes[0].path;
        asyncRoutes.push(homeRoute);
      }
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

