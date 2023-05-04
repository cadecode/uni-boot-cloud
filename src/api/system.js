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

export function listUserRolesVoByUserIds(data) {
  return request({
    url: '/system/user/list_roles_vo_by_user_ids',
    method: 'post',
    data
  });
}

export function listRole() {
  return request({
    url: '/system/role/list',
    method: 'post'
  });
}

export function addRoleUser(data){
  return request({
    url: '/system/role/add_user_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加用户角色绑定'
  });
}

export function removeRoleUser(data){
  return request({
    url: '/system/role/remove_user_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除用户角色绑定'
  });
}

