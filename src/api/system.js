import request from '@/util/request';

export function pageUserRolesVo(data) {
  return request({
    url: '/system/user/page_roles_vo',
    method: 'post',
    data
  });
}

export function updateUserEnable(data) {
  return request({
    url: '/system/user/update_enable',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新启用状态'
  });
}

export function updateUser(data) {
  return request({
    url: '/system/user/update',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新用户'
  });
}

export function addUser(data) {
  return request({
    url: '/system/user/add',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加用户'
  });
}

export function deleteUser(data) {
  return request({
    url: '/system/user/delete',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除用户'
  });
}

export function listRole() {
  return request({
    url: '/system/role/list',
    method: 'post'
  });
}

