import request from '@/util/request';

export function modifyInfo(data) {
  return request({
    url: '/framework/system/user/modify_info',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新用户信息'
  });
}

export function modifyPass(data) {
  return request({
    url: '/framework/system/user/modify_pass',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新用户密码'
  });
}
