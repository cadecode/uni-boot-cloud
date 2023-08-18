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
    VALUE (1, null, 'Home', '/home/demo_home', '/Home/DemoHome', '首页', 1, 1, 1);

INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (2, null, 'System', '/system', null, '系统管理', 0, 2, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (201, 2, 'User', '/system/user', '/System/User', '用户管理', 1, 201, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (202, 2, 'Role', '/system/role', '/System/Role', '角色管理', 1, 202, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (203, 2, 'Menu', '/system/menu', '/System/Menu', '菜单管理', 1, 203, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (204, 2, 'Api', '/system/api', '/System/Api', 'API管理', 1, 204, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (205, 2, 'Dict', '/system/dict', '/System/Dict', '字典管理', 1, 205, 1);

INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (3, null, 'Develop', '/develop', null, '开发管理', 0, 2, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (301, 3, 'Log', '/develop/log', '/Develop/Log', '日志管理', 1, 301, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (306, 3, 'Icon', '/develop/icon', '/Develop/Icon', '图标', 1, 306, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (307, 3, 'Echarts', '/develop/echarts', '/Develop/Echarts', 'Echarts 示例', 1, 307, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (308, 3, 'Swagger', 'http://localhost:8000/doc.html', null, 'Swagger', 1, 308, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (309, 3, 'Druid', 'http://localhost:8000/druid', null, 'Druid', 1, 309, 1);

INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (1000, null, 'GithubUrl', 'https://github.com/cadecode/uni-boot-admin', null, '源码仓库', 1, 1000, 1);
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
VALUES (1, 201);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 202);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 203);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 204);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 205);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 3);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 301);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 306);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 307);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 308);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 309);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 1000);

INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 1);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 2);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 201);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 202);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 203);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 204);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 205);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 1000);

INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 1);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 1000);
-- ----------------------------
-- 插入角色API关系
-- ----------------------------
INSERT INTO sys_role_api (role_id, api_id)
VALUES (1, 1);
INSERT INTO sys_role_api (role_id, api_id)
VALUES (2, 1);
INSERT INTO sys_role_api (role_id, api_id)
VALUES (3, 1);
-- ----------------------------
-- 插入字典
-- ----------------------------
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1, '日志类型', 'logType', '查询', 'Query', '日志类型-查询', 0, 0, '2023-06-11 23:24:26',
        '2023-06-12 00:11:57', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (2, '日志类型', 'logType', '删除', 'Remove', '日志类型-删除', 1, 0, '2023-06-11 23:24:52',
        '2023-06-12 00:12:01', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (3, '日志类型', 'logType', '更新', 'Update', '日志类型-更新', 2, 0, '2023-06-11 23:25:08',
        '2023-06-12 00:12:05', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (4, '日志类型', 'logType', '添加', 'Add', '日志类型-添加', 3, 0, '2023-06-11 23:25:29',
        '2023-06-12 00:12:09', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (5, '日志类型', 'logType', '鉴权', 'Auth', '日志类型-鉴权', 4, 0, '2023-06-11 23:25:45',
        '2023-06-12 00:12:15', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (6, '日志类型', 'logType', '导入', 'Import', '日志类型-导入', 5, 0, '2023-06-11 23:25:58',
        '2023-06-12 00:12:19', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (7, '日志类型', 'logType', '导出', 'Export', '日志类型-导出', 6, 0, '2023-06-11 23:26:09',
        '2023-06-12 00:12:30', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (8, '日志类型', 'logType', '上传', 'Upload', '日志类型-上传', 7, 0, '2023-06-11 23:26:26',
        '2023-06-12 00:12:35', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (9, '日志类型', 'logType', '下载', 'Download', '日志类型-下载', 8, 0, '2023-06-11 23:26:40',
        '2023-06-12 00:12:40', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (10, '日志类型', 'logType', '其他', 'Other', '日志类型-其他', 9, 0, '2023-06-11 23:26:49',
        '2023-06-12 00:52:14', 'admin');
