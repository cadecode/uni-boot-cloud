import request from '@/utils/request'
import defaultSettings from '@/settings'

const { loginUrl, logoutUrl } = defaultSettings

export function login(data) {
  return request({
    url: loginUrl,
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: logoutUrl,
    method: 'post'
  })
}

export function getInfo(token) {
  return request({
    url: '/system/user/get_info',
    method: 'post'
  })
}
