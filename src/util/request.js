/**
 * http工具
 */
import axios from 'axios';
import {Message, MessageBox} from 'element-ui';
import store from '@/store';
import router from '@/router';
import {getToken} from '@/util/cookie';
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
 * 后端响应格式：
 *  {status, data, error:{code, message, path, moreInfo}}
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
    Message.error({message: error.message, duration: 5 * 1000});
    throw error;
  }
);

/**
 * @typedef {Object} ApiError
 * @property {string} code 错误码
 * @property {string} message 信息
 * @property {string} path 请求路径
 * @property {string} moreInfo 更多信息
 */

/**
 * @typedef {Object} ApiResult
 * @property {number} status 状态码
 * @property {Object} data 数据
 * @property {ApiError} error 错误
 */

/**
 * 从响应中检查后端 error 信息
 * @param {AxiosResponse<ApiResult>>} response 响应对象
 */
const confirmReLogin = getReLoginConfirm();
function checkResError(response) {
  const res = response.data;
  // 判断有没有返回 error
  if (!res || !res.error) {
    return;
  }
  // 401 未登录，跳转到登录页
  if (res.status === 401) {
    return confirmReLogin(res);
  }
  let errorMessage;
  if (res.error.message) {
    errorMessage = res.error.moreInfo ? `${res.error.message}, ${res.error.moreInfo}` : res.error.message;
  } else {
    // 此处 res.error 可兼容 SpringBoot 原生接口异常
    errorMessage = res.error;
  }
  Message.error({message: errorMessage, duration: 5 * 1000});
  throw new Error(JSON.stringify(res.error));
}

/**
 * 确认是否重新登录
 * await 返回结果结果将抛出错误
 */
function getReLoginConfirm() {
  let confirming = false;
  return (res) => {
    if (confirming) {
      return;
    }
    confirming = true;
    return MessageBox.confirm(`${res.error.message}, 是否重新登录`, '登录状态失效', {
      type: 'warning',
      confirmButtonText: '返回登录页',
      cancelButtonText: '取消'
    }).then(async() => {
      confirming = false;
      // 使用 async 包裹，可等待完成 resetToken
      // 清理 token 并返回登录页
      await store.dispatch('user/resetToken');
      router.push(`/login?redirect=${router.currentRoute.fullPath}`);
      throw new Error(JSON.stringify(res.error));
    });
  };
}

/**
 * 从响应中检查后端token信息，每次写入新token
 * @param {AxiosResponse<ApiResult>>} response 响应对象
 */
function checkResToken(response) {
  if (response.headers && response.headers[tokenKey]) {
    return store.dispatch('user/setToken', response.headers[tokenKey]);
  }
}

/**
 * @typedef {Object} RequestCustomConfig 请求自定义配置
 * @property {function} messageFn res => res.data.flag，根据flag决定是否弹出提示
 * @property {string} messagePrefix 提示内容的前缀
 */

/**
 * 发送请求
 * @param {Object} config axios配置
 * @param {RequestCustomConfig} customConfig 自定义配置
 */
function request(config, customConfig = {}) {
  return service(config).then(res => {
    if (customConfig && customConfig.messageFn) {
      let flag;
      try {
        flag = customConfig.messageFn(res);
      } catch (e) {
        flag = false;
      }
      const prefix = customConfig.messagePrefix ? customConfig.messagePrefix + '，' : '';
      // 操作成功
      if (flag) {
        Message.success(`${prefix}操作成功`);
      } else {
        Message.error(`${prefix}操作失败`);
      }
    }
    return res;
  });
}

/**
 * FormData式请求，new FormData()
 * axios自动设置 multipart/form-data
 * @param {Object} config axios配置
 * @param {RequestCustomConfig} customConfig 自定义配置
 */
function requestFormData(config, customConfig = {}) {
  config.data = Object.keys(config.data).reduce((p, n) => {
    p.append(n, config.data[n]);
    return p;
  }, new FormData());
  return request(config, customConfig);
}

export {
  request as default,
  requestFormData
};
