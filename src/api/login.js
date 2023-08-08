import request, {requestFormData} from '@/util/request';
import settings from '@/settings';

const {loginUrl, logoutUrl} = settings;

export function login(data) {
  return requestFormData({
    url: loginUrl,
    method: 'post',
    data
  });
}

export function logout() {
  return request({
    url: logoutUrl,
    method: 'post'
  });
}

export function getInfo(token) {
  return request({
    url: '/framework/system/user/get_info',
    method: 'post'
  });
}
