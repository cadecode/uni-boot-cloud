/**
 * http工具
 */
import axios from 'axios';
import {Message, MessageBox} from 'element-ui';
import store from '@/store';
import router from '@/router';
import {getToken} from '@/util/cookie';
import settings from '@/settings';
import {objectToQuery} from '@/util/common';

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
 * uni-boot-admin 响应格式：
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
function checkResError(response) {
  const res = response.data;
  let errorMessage;
  // 判断有没有返回error
  if (res && res.error) {
    // 401未登录，跳转到登录页
    if (res.status === 401) {
      errorMessage = res.error.message;
      // await此结果将抛出错误
      return MessageBox.confirm(`${errorMessage}, 是否重新登录`, '登录状态失效', {
        type: 'warning',
        confirmButtonText: '返回登录页',
        cancelButtonText: '取消'
      }).then(async() => {
        // 清理token并返回登录页
        await store.dispatch('user/resetToken');
        router.push(`/login?redirect=${router.currentRoute.fullPath}`);
        throw new Error(errorMessage);
      });
    }
    errorMessage = 'Error: ' + JSON.stringify(res.error);
    // 此处res.error可兼容SpringBoot原生接口异常
    Message.error({message: errorMessage, duration: 5 * 1000});
    throw new Error(errorMessage);
  }
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
function request(config, customConfig) {
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
 * 查询字符串式的请求，a=1&b=2
 * axios自动设置x-www-form-urlencoded
 * @param {Object} config axios配置
 * @param {RequestCustomConfig} customConfig 自定义配置
 */
function requestQuery(config, customConfig) {
  config.data = objectToQuery((config.data));
  return request(config, customConfig);
}

/**
 * FormData式请求，new FormData()
 * axios自动设置x-www-form-urlencoded
 * @param {Object} config axios配置
 * @param {RequestCustomConfig} customConfig 自定义配置
 */
function requestFormData(config, customConfig) {
  config.data = Object.keys(config.data).reduce((p, n) => {
    p.append(n, config.data[n]);
    return p;
  }, new FormData());
  return request(config, customConfig);
}

export {
  request as default,
  requestFormData,
  requestQuery
};
