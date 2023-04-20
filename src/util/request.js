/**
 * http工具
 */
import axios from 'axios';
import {Message, MessageBox} from 'element-ui';
import store from '@/store';
import router from '@/router';
import {getToken} from '@/util/token';
import settings from '@/settings';

const {tokenKey} = settings;

const service = axios.create({
  // url = base url + request url
  baseURL: process.env.VUE_APP_BASE_API,
  // 跨域时是否携带cookie
  // withCredentials: true,
  // 超时时间
  timeout: 60 * 1000
});

/**
 * 请求拦截器
 */
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers[tokenKey] = getToken();
    }
    return config;
  },
  error => {
    console.error(error);
    throw error;
  }
);

/**
 * 响应拦截器
 * uni-boot-admin 响应格式 {status, data, error:{code, message, path, moreInfo}}
 */
service.interceptors.response.use(
  async(response) => {
    const res = response.data;
    await checkResError(response);
    await checkResToken(response);
    return res;
  },
  async(error) => {
    console.error(error);
    const response = error.response;
    // 存在返回的json数据
    if (response && response.data) {
      await checkResError(response);
    }
    Message({message: error.message, type: 'error', duration: 5 * 1000});
    throw error;
  }
);

/**
 * 从响应中检查后端error信息
 */
function checkResError(response) {
  const res = response.data;
  let errorMessage;
  // 判断有没有返回error
  if (res && res.error) {
    // 401未登录，跳转到登录页
    if (res.status === 401) {
      errorMessage = res.error.message;
      return MessageBox.confirm(errorMessage + ', 是否重新登录', '登录状态失效', {
        confirmButtonText: '返回登录页',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        // 清理token并返回登录页
        await store.dispatch('user/resetToken');
        router.push(`/login?redirect=${router.currentRoute.fullPath}`);
        throw new Error(errorMessage);
      });
    }
    errorMessage = '错误: ' + JSON.stringify(res.error);
    // 此处res.error可兼容SpringBoot原生接口异常
    Message({message: errorMessage, type: 'error', duration: 5 * 1000});
    throw new Error(errorMessage);
  }
}

/**
 * 从响应中检查后端token信息，每次写入新token
 */
function checkResToken(response) {
  if (response.headers && response.headers[tokenKey]) {
    return store.dispatch('user/setToken', response.headers[tokenKey]);
  }
}

export default service;
