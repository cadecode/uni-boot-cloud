-- 导入初始数据

-- ----------------------------
-- 插入用户
-- 密码使用 BCryptPasswordEncoder 生成，此处123456
-- ----------------------------
INSERT INTO sys_user (id, username, password, nick_name, dept_id, enable_flag)
VALUES (1, 'admin', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '管理员', 1, '1');
INSERT INTO sys_user (id, username, password, nick_name, dept_id, enable_flag)
VALUES (2, 'manager01', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '高级用户01', 1, '1');
INSERT INTO sys_user (id, username, password, nick_name, dept_id, enable_flag)
VALUES (3, 'user01', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '普通用户01', 1, '1');
-- ----------------------------
-- 插入角色
-- ----------------------------
INSERT INTO sys_role (id, code, name, description)
VALUES (1, 'admin', '管理员用户', '管理员权限');
INSERT INTO sys_role (id, code, name, description)
VALUES (2, 'manager', '高级用户', '高级用户权限');
INSERT INTO sys_role (id, code, name, description)
VALUES (3, 'normal', '普通用户', '普通用户权限');
-- ----------------------------
-- 插入菜单
-- ----------------------------
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (1, null, 'DemoHome', '/home/demo_home', '/Home/DemoHome', '首页', 1, 'el-icon-s-home', 1, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:26', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:44:14', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (2, null, 'System', '/system', '/system', '系统管理', 0, 'el-icon-setting', 2, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:26', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:28:48', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (3, null, 'DevelopManagement', '/develop', '/develop', '开发管理', 0, 'el-icon-s-platform', 3, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:29', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:45:10', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (201, 2, 'UserManagement', '/system/user', '/System/User', '用户管理', 1, null, 201, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:27', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:44:52', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (202, 2, 'RoleManagement', '/system/role', '/System/Role', '角色管理', 1, null, 202, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:27', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:44:55', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (203, 2, 'MenuManagement', '/system/menu', '/System/Menu', '菜单管理', 1, null, 203, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:28', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:44:59', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (204, 2, 'DeptManagement', '/system/dept', '/System/Dept', '部门管理', 1, null, 204, 1, 0, 0,
        TO_DATE('2023-11-25 09:46:20', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-26 10:05:19', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (205, 2, 'ApiManagement', '/system/api', '/System/Api', 'API管理', 1, null, 205, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:28', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-26 10:05:19', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (206, 2, 'DictManagement', '/system/dict', '/System/Dict', '字典管理', 1, null, 206, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:29', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-26 10:05:19', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (301, 3, 'LogManagement', '/develop/log', '/Develop/Log', '日志管理', 1, null, 301, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:30', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:45:14', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (302, 3, 'MqMsgManagement', '/develop/mq_msg', '/Develop/MqMsg', '消息队列', 1, null, 302, 1, 0, 0,
        TO_DATE('2023-08-22 23:23:46', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:45:31', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (303, 3, 'FileManagement', '/develop/file', '/Develop/File', '文件管理', 1, null, 303, 1, 0, 0,
        TO_DATE('2023-08-22 23:23:46', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:45:44', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (306, 3, 'Icon', '/develop/icon', '/Develop/Icon', '图标', 1, null, 306, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:30', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 15:57:56', 'yyyy-MM-dd HH24:mi:ss'), null);
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (307, 3, 'EchartsDemo', '/develop/echarts', '/Develop/Echarts', 'Echarts 示例', 1, null, 307, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:30', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 16:46:04', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (308, 3, 'Swagger', 'http://localhost:8000/doc.html', null, 'Swagger', 1, null, 308, 1, 0, 0,
        TO_DATE('2023-08-18 15:03:30', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 15:57:55', 'yyyy-MM-dd HH24:mi:ss'), null);
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (1000, null, 'GithubUrl', 'https://github.com/cadecode/uni-boot-admin', null, '源码仓库', 1, 'el-icon-star-on',
        1000, 0, 0, 0, TO_DATE('2023-08-18 15:03:31', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 15:57:55', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_menu (id, parent_id, route_name, route_path, component_path, menu_name, leaf_flag, icon, order_num,
                      enable_flag, hidden_flag, cache_flag, create_time, update_time, update_user)
VALUES (1001, null, 'UserCenter', '/user_center', '/UserCenter', '个人中心', 1, null, null, 1, 1, 0,
        TO_DATE('2023-11-07 15:54:25', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-11-09 17:57:17', 'yyyy-MM-dd HH24:mi:ss'), 'admin');

-- ----------------------------
-- 插入 API
-- ----------------------------
INSERT INTO sys_api(id, url, description)
VALUES (1, '/**', '默认放通全部接口');
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
VALUES (1, 206);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 301);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 302);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (1, 303);
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
VALUES (1, 1001);
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
VALUES (2, 206);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 1000);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (2, 1001);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 1);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 1000);
INSERT INTO sys_role_menu (role_id, menu_id)
VALUES (3, 1001);

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
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (1, '日志类型', 'logType', '查询', 'Query', '日志类型-查询', 0, 0,
        TO_DATE('2023-06-11 23:24:26', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:11:57', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (2, '日志类型', 'logType', '删除', 'Remove', '日志类型-删除', 1, 0,
        TO_DATE('2023-06-11 23:24:52', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:01', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (3, '日志类型', 'logType', '更新', 'Update', '日志类型-更新', 2, 0,
        TO_DATE('2023-06-11 23:25:08', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:05', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (4, '日志类型', 'logType', '添加', 'Add', '日志类型-添加', 3, 0,
        TO_DATE('2023-06-11 23:25:29', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:09', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (5, '日志类型', 'logType', '鉴权', 'Auth', '日志类型-鉴权', 4, 0,
        TO_DATE('2023-06-11 23:25:45', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:15', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (6, '日志类型', 'logType', '导入', 'Import', '日志类型-导入', 5, 0,
        TO_DATE('2023-06-11 23:25:58', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:19', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (7, '日志类型', 'logType', '导出', 'Export', '日志类型-导出', 6, 0,
        TO_DATE('2023-06-11 23:26:09', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:30', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (8, '日志类型', 'logType', '上传', 'Upload', '日志类型-上传', 7, 0,
        TO_DATE('2023-06-11 23:26:26', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:35', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (9, '日志类型', 'logType', '下载', 'Download', '日志类型-下载', 8, 0,
        TO_DATE('2023-06-11 23:26:40', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:12:40', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (10, '日志类型', 'logType', '其他', 'Other', '日志类型-其他', 9, 0,
        TO_DATE('2023-06-11 23:26:49', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-06-12 00:52:14', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (11, '服务URL', 'serviceUrl', '框架服务', '/framework', 'framework 服务 url 前缀', 1, 1,
        TO_DATE('2023-08-18 15:09:16', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-08-24 10:20:50', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (12, '服务URL', 'serviceUrl', '示例服务', '/example', 'example 服务 url 前缀', 2, 0,
        TO_DATE('2023-08-18 15:09:30', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-08-24 10:20:51', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (13, 'MQ消息发送状态', 'mqMsgSendState', '处理中', 'PREPARING', 'MQ 消息发送中', 1, 1,
        TO_DATE('2023-08-22 23:25:52', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-08-24 10:20:50', 'yyyy-MM-dd HH24:mi:ss'), 'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (14, 'MQ消息发送状态', 'mqMsgSendState', '失败', 'FAIL', 'MQ 消息发送失败', 2, 1,
        TO_DATE('2023-08-22 23:26:04', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-08-24 10:20:51', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
INSERT INTO sys_dict (id, name, type, label, value, description, order_num, default_flag, create_time,
                      update_time, update_user)
VALUES (15, 'MQ消息发送状态', 'mqMsgSendState', '完成', 'OVER', 'MQ 消息发送完成', 3, 0,
        TO_DATE('2023-08-22 23:26:10', 'yyyy-MM-dd HH24:mi:ss'),
        TO_DATE('2023-08-24 10:20:50', 'yyyy-MM-dd HH24:mi:ss'),
        'admin');
-- ----------------------------
-- 插入部门
-- ----------------------------
INSERT INTO sys_dept (id, dept_name, parent_id, order_num, leader, mail, phone, create_time, update_time, update_user)
VALUES (1, '测试部门1', null, 1, '张三', 'test1@mail.com', '123456',
        TO_DATE('2023-11-26 08:34:19', 'yyyy-MM-dd HH24:mi:ss'), null, 'admin');
INSERT INTO sys_dept (id, dept_name, parent_id, order_num, leader, mail, phone, create_time, update_time, update_user)
VALUES (2, '测试部门2', null, 2, '', null, '', TO_DATE('2023-11-26 08:35:04', 'yyyy-MM-dd HH24:mi:ss'), null, 'admin');
INSERT INTO sys_dept (id, dept_name, parent_id, order_num, leader, mail, phone, create_time, update_time, update_user)
VALUES (11, '测试科室1-1', 1, 11, '', null, '', TO_DATE('2023-11-26 08:34:53', 'yyyy-MM-dd HH24:mi:ss'), null, 'admin');

