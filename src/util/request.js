import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import router from '@/router'
import { getToken } from '@/util/auth'
import settings from '@/settings'

const { tokenKey } = settings

const service = axios.create({
  // url = base url + request url
  baseURL: process.env.VUE_APP_BASE_API,
  // 跨域时是否携带cookie
  // withCredentials: true,
  // 超时时间
  timeout: 60 * 1000
})

/**
 * 请求拦截器
 */
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers[tokenKey] = getToken()
    }
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

/**
 * 响应拦截器
 * uni-boot-admin 响应格式 {status, data, error:{code, message, path, moreInfo}}
 */
service.interceptors.response.use(
  response => {
    const res = response.data
    checkResError(response)
    checkResToken(response)
    return res
  },
  (error) => {
    console.error(error)
    const response = error.response
    if (response && response.data) {
      checkResError(response)
      return
    }
    Message({ message: error.message, type: 'error', duration: 5 * 1000 })
    throw error
  }
)

/**
 * 从响应中检查后端error信息
 */
function checkResError(response) {
  const res = response.data
  let errorMessage
  // 判断有没有返回error
  if (res && res.error) {
    // 未登录跳转到登录页
    if (res.status === 401) {
      errorMessage = res.error.message + ', 是否重新登录'
      MessageBox.confirm(errorMessage, '登录状态失效', {
        confirmButtonText: '返回登录页',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        // 清理token并返回登录页
        await store.dispatch('user/resetToken')
        router.push(`/login?redirect=${router.currentRoute.fullPath}`)
      })
      return
    }
    errorMessage = '错误: ' + JSON.stringify(res.error)
    // 此处res.error可兼容SpringBoot原生接口异常
    Message({
      message: errorMessage || '未知错误',
      type: 'error',
      duration: 5 * 1000,
      dangerouslyUseHTMLString: true
    })
    throw new Error(errorMessage || '未知错误')
  }
}

/**
 * 从响应中检查后端token信息，每次写入新token
 */
function checkResToken(response) {
  if (response.headers && response.headers[tokenKey]) {
    store.dispatch('user/setToken', response.headers[tokenKey])
  }
}

export default service
