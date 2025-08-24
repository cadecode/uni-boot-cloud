import store from '@/store';

/**
 * 给定一组角色列表，判断当前用户是否包含其中的角色
 *
 * @param {string[]} value
 * @returns {boolean}
 */
function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const roles = store.getters && store.getters.roles;
    const permissionRoles = value;
    return roles.some(role => {
      return permissionRoles.includes(role);
    });
  } else {
    return false;
  }
}

/**
 * 拼接角色和 type 和 code
 */
function getRoleTypeCode(role) {
  return `${role.type}:${role.code}`;
}

const ROLE_TYPE_ACCESS = 'ACCESS';
const ROLE_TYPE_DATA = 'DATA';
const ROLE_TYPES = [
  {
    label: '访问角色',
    value: ROLE_TYPE_ACCESS
  },
  {
    label: '数据角色',
    value: ROLE_TYPE_DATA
  }];
const roleTypes = {
  ROLE_TYPE_ACCESS,
  ROLE_TYPE_DATA,
  ROLE_TYPES
};

export {
  checkPermission,
  getRoleTypeCode,
  roleTypes
};
