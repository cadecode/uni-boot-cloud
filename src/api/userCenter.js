import request from '@/util/request';

export function modifyInfo(data) {
  return request({
    url: '/system/user/modify_info',
    method: 'post',
    data
  }, {
    messageFn: res => res.data
  });
}

export function modifyPass(data) {
  return request({
    url: '/system/user/modify_pass',
    method: 'post',
    data
  }, {
    messageFn: res => res.data
  });
}
