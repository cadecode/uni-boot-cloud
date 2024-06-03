/**
 * token持久化工具
 */
import Cookies from 'js-cookie';
import settings from '@/settings';

const {tokenKey} = settings;

/**
 * store app
 */
function getSidebarStatus() {
  return Cookies.get('sidebarStatus') ? Boolean(Cookies.get('sidebarStatus')) : true;
}

function setSidebarStatus(status) {
  Cookies.set('sidebarStatus', status);
}

/**
 * store user
 */
function getToken() {
  return Cookies.get(tokenKey);
}

function setToken(token) {
  return Cookies.get(tokenKey) || token && Cookies.set(tokenKey, token);
}

function removeToken() {
  return Cookies.remove(tokenKey);
}

function getName() {
  return Cookies.get('name');
}

function setName(name) {
  name && Cookies.set('name', name);
}

function getAvatar() {
  return Cookies.get('avatar');
}

function setAvatar(avatar) {
  avatar && Cookies.set('avatar', avatar);
}

function getRoles() {
  return Cookies.get('roles') ? JSON.parse(Cookies.get('roles')) : [];
}

function setRoles(roles) {
  roles && Cookies.set('roles', JSON.stringify(roles));
}

function getUserInfo() {
  return Cookies.get('userInfo') ? JSON.parse(Cookies.get('userInfo')) : {};
}

function setUserInfo(userInfo) {
  userInfo && Cookies.set('userInfo', JSON.stringify(userInfo));
}

export {
  getSidebarStatus,
  setSidebarStatus,
  getToken,
  setToken,
  removeToken,
  getName,
  setName,
  getAvatar,
  setAvatar,
  getRoles,
  setRoles,
  getUserInfo,
  setUserInfo
};
