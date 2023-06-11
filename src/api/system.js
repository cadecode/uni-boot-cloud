import request, {requestQuery} from '@/util/request';
// user
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

export function addRoleUser(data) {
  return request({
    url: '/system/role/add_user_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加用户角色绑定'
  });
}

export function removeRoleUser(data) {
  return request({
    url: '/system/role/remove_user_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除用户角色绑定'
  });
}
// menu
export function pageMenuRolesVo(data) {
  return request({
    url: '/system/menu/page_roles_vo',
    method: 'post',
    data
  });
}

export function updateMenuEnable(data) {
  return request({
    url: '/system/menu/update_enable',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新启用状态'
  });
}

export function updateMenu(data) {
  return request({
    url: '/system/menu/update',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新菜单'
  });
}

export function addMenu(data) {
  return request({
    url: '/system/menu/add',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加菜单'
  });
}

export function deleteMenu(data) {
  return request({
    url: '/system/menu/delete',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除菜单'
  });
}

export function listMenuRolesVoByMenuIds(data) {
  return request({
    url: '/system/menu/list_roles_vo_by_menu_ids',
    method: 'post',
    data
  });
}

export function addRoleMenu(data) {
  return request({
    url: '/system/role/add_menu_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加菜单角色绑定'
  });
}

export function removeRoleMenu(data) {
  return request({
    url: '/system/role/remove_menu_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除菜单角色绑定'
  });
}
// api
export function pageApiRolesVo(data) {
  return request({
    url: '/system/api/page_roles_vo',
    method: 'post',
    data
  });
}

export function updateApi(data) {
  return request({
    url: '/system/api/update',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新API'
  });
}

export function addApi(data) {
  return request({
    url: '/system/api/add',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加API'
  });
}

export function deleteApi(data) {
  return request({
    url: '/system/api/delete',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除API'
  });
}

export function listApiRolesVoByApiIds(data) {
  return request({
    url: '/system/api/list_roles_vo_by_api_ids',
    method: 'post',
    data
  });
}

export function addRoleApi(data) {
  return request({
    url: '/system/role/add_api_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加API角色绑定'
  });
}

export function removeRoleApi(data) {
  return request({
    url: '/system/role/remove_api_mapping',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除API角色绑定'
  });
}

export function listApiSwaggerVo(data) {
  return request({
    url: '/system/api/list_swagger_vo',
    method: 'post',
    data
  });
}
// role
export function updateRole(data) {
  return request({
    url: '/system/role/update',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新角色'
  });
}

export function addRole(data) {
  return request({
    url: '/system/role/add',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加角色'
  });
}

export function deleteRole(data) {
  return request({
    url: '/system/role/delete',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除角色'
  });
}

export function pageRoleUnionVo(data) {
  return request({
    url: '/system/role/page_union_vo',
    method: 'post',
    data
  });
}

export function listRoleUnionVoByRoleIds(data) {
  return request({
    url: '/system/role/list_union_vo_by_role_ids',
    method: 'post',
    data
  });
}

export function pageLog(data) {
  return request({
    url: '/system/log/page',
    method: 'post',
    data
  });
}

export function deleteLog(data) {
  return request({
    url: '/system/log/delete',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除日志'
  });
}

export function pageDict(data) {
  return request({
    url: '/system/dict/page',
    method: 'post',
    data
  });
}

export function deleteDict(data) {
  return request({
    url: '/system/dict/delete',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '删除日志'
  });
}

export function updateDict(data) {
  return request({
    url: '/system/dict/update',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '更新字典'
  });
}

export function addDict(data) {
  return request({
    url: '/system/dict/add',
    method: 'post',
    data
  }, {
    messageFn: res => res.data,
    messagePrefix: '添加字典'
  });
}

export function listDictByType(type) {
  return request({
    url: '/system/dict/list_by_type',
    method: 'get',
    params: {
      type
    }
  });
}

export function listDictByIds(data) {
  return request({
    url: '/system/dict/list_by_ids',
    method: 'post',
    data
  });
}

export function listDictType() {
  return request({
    url: '/system/dict/list_type',
    method: 'post'
  });
}
