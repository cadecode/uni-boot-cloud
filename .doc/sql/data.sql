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
    VALUE (1, null, 'Home', '/home/demo_home', '/Home/DemoHome', '首页', 1, 0, 1);

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
    VALUE (7, 2, 'Log', '/system/log', '/System/Log', '日志管理', 1, 105, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (8, 2, 'Dict', '/system/dict', '/System/Dict', '字典管理', 1, 106, 1);

INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (9, null, 'Develop', '/develop', null, '开发管理', 0, 2, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (10, 9, 'Icon', '/develop/icon', '/Develop/Icon', '图标', 1, 201, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (11, 9, 'Echarts', '/develop/echarts', '/Develop/Echarts', 'Echarts 示例', 1, 202, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (12, 9, 'Swagger', 'http://localhost:8000/doc.html', null, 'Swagger', 1, 203, 1);
INSERT INTO sys_menu(id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, order_num,
                     enable_flag)
    VALUE (13, 9, 'Druid', 'http://localhost:8000/druid', null, 'Druid', 1, 204, 1);

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
VALUES (1, 12);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 13);
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
VALUES (2, 7);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 8);
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
-- ----------------------------
-- 插入字典
-- ----------------------------
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667915699808002049, '日志类型', 'logType', '查询', 'Query', '日志类型-查询', 0, 0, '2023-06-11 23:24:26',
        '2023-06-12 00:11:57', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667915811166773250, '日志类型', 'logType', '删除', 'Remove', '日志类型-删除', 1, 0, '2023-06-11 23:24:52',
        '2023-06-12 00:12:01', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667915876962820097, '日志类型', 'logType', '更新', 'Update', '日志类型-更新', 2, 0, '2023-06-11 23:25:08',
        '2023-06-12 00:12:05', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667915965307445249, '日志类型', 'logType', '添加', 'Add', '日志类型-添加', 3, 0, '2023-06-11 23:25:29',
        '2023-06-12 00:12:09', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667916031808135169, '日志类型', 'logType', '鉴权', 'Auth', '日志类型-鉴权', 4, 0, '2023-06-11 23:25:45',
        '2023-06-12 00:12:15', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667916084467621889, '日志类型', 'logType', '导入', 'Import', '日志类型-导入', 5, 0, '2023-06-11 23:25:58',
        '2023-06-12 00:12:19', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667916134056878081, '日志类型', 'logType', '导出', 'Export', '日志类型-导出', 6, 0, '2023-06-11 23:26:09',
        '2023-06-12 00:12:30', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667916204034646018, '日志类型', 'logType', '上传', 'Upload', '日志类型-上传', 7, 0, '2023-06-11 23:26:26',
        '2023-06-12 00:12:35', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667916260599029762, '日志类型', 'logType', '下载', 'Download', '日志类型-下载', 8, 0, '2023-06-11 23:26:40',
        '2023-06-12 00:12:40', 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time, update_time,
                      update_user)
VALUES (1667916298465206274, '日志类型', 'logType', '其他', 'Other', '日志类型-其他', 9, 0, '2023-06-11 23:26:49',
        '2023-06-12 00:52:14', 'admin');
