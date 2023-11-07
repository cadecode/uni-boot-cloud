import store from '@/store';

/**
 * 给定一组角色列表，判断当前用户是否包含其中的角色
 *
 * @param {Array<String>} value
 * @returns {Boolean}
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

export {
  checkPermission
};
