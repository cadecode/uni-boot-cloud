module.exports = {

  title: 'Uni Boot Admin 管理系统',

  /**
   * 是否固定头部
   */
  fixedHeaderFlag: true,

  /**
   * 是否显示侧边栏logo
   */
  sidebarLogoFlag: true,

  /**
   * 侧边栏logo src
   * 支持 http(s) 资源链接或 asset 目录下资源路径（从 asset下级目录开始）
   */
  sidebarLogoSrc: '',

  /**
   * cookie或header存放token的key
   */
  tokenKey: 'token',

  /**
   * 登录url
   */
  loginUrl: '/framework/auth/login',

  /**
   * 注销url
   */
  logoutUrl: '/framework/auth/logout'
};
