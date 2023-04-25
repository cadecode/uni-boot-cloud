import request from '@/util/request';

export function listRole() {
  return request({
    url: '/system/role/list',
    method: 'post'
  });
}

export function pageUserRolesVo(data) {
  return request({
    url: '/system/user/page_roles_vo',
    method: 'post',
    data
  });
}
