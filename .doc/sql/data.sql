-- 导入初始数据

-- ----------------------------
-- 插入用户
-- 密码使用 BCryptPasswordEncoder 生成，此处123456
-- ----------------------------
INSERT INTO sys_user (id, username, password, nick_name, enable_flag)
    VALUE (1, 'admin', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '管理员', '1');
INSERT INTO sys_user (id, username, password, nick_name, enable_flag)
    VALUE (2, 'manager01', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '高级用户01', '1');
INSERT INTO sys_user (id, username, password, nick_name, enable_flag)
    VALUE (3, 'user01', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '普通用户01', '1');
-- ----------------------------
-- 插入角色
-- ----------------------------
INSERT INTO sys_role (id, code, name, description)
    VALUE (1, 'admin', '管理员用户', '管理员权限');
INSERT INTO sys_role (id, code, name, description)
    VALUE (2, 'manager', '高级用户', '高级用户权限');
INSERT INTO sys_role (id, code, name, description)
    VALUE (3, 'normal', '普通用户', '普通用户权限');
-- ----------------------------
-- 插入菜单
-- ----------------------------
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (1, null, 'Home', '/home', '/Home', '首页', 1, 0, 1);

INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (2, null, 'System', '/system', null, '系统管理', 0, 1, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (3, 2, 'User', '/system/user', '/System/User', '用户管理', 1, 101, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (4, 2, 'Role', '/system/role', '/System/Role', '角色管理', 1, 102, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (5, 2, 'Menu', '/system/menu', '/System/Menu', '菜单管理', 1, 103, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (6, 2, 'Api', '/system/api', '/System/Api', 'API管理', 1, 104, 1);

INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (7, null, 'Develop', '/develop', null, '开发管理', 0, 2, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (8, 7, 'Icon', '/develop/icon', '/Develop/Icon', '图标', 1, 201, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (9, 7, 'Swagger', 'http://localhost:8080/doc.html', null, 'Swagger', 1, 202, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (10, 7, 'Cache', '/develop/cache', '/Develop/Cache', '缓存管理', 1, 203, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (11, 7, 'ServerInfo', '/develop/server_info', '/Develop/ServerInfo', '服务器信息', 1,
           204, 1);

INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (100, null, 'GithubUrl', 'https://github.com/cadecode/uni-boot-admin', null, '源码仓库', 1, 100, 1);
-- ----------------------------
-- 插入 API
-- ----------------------------
INSERT INTO sys_api(id, url, description)
    VALUE (1, '/**', '默认放通全部接口');
-- ----------------------------
-- 插入角色用户关系
-- ----------------------------
INSERT INTO sys_role_user (role_id, user_id)
VALUES (1, 1);
INSERT INTO sys_role_user (role_id, user_id)
VALUES (2, 2);
INSERT INTO sys_role_user (role_id, user_id)
VALUES (3, 3);
-- ----------------------------
-- 插入角色菜单关系
-- ----------------------------
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 1);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 2);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 3);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 4);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 5);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 6);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 7);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 8);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 9);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 10);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 11);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 100);

INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 1);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 2);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 3);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 4);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 5);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 6);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 100);

INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 1);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 100);
-- ----------------------------
-- 插入角色API关系
-- ----------------------------
INSERT INTO sys_role_api (role_id, api_id)
VALUES (1, 1);
INSERT INTO sys_role_api (role_id, api_id)
VALUES (2, 1);
INSERT INTO sys_role_api (role_id, api_id)
VALUES (3, 1);
