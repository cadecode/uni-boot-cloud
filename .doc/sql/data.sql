-- ----------------------------
-- 插入用户
-- 密码使用 BCryptPasswordEncoder 生成
-- ----------------------------
INSERT INTO sys_user (id, username, password, nick_name, enable_flag)
    VALUE (1, 'user', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '用户', '1');
INSERT INTO sys_user (id, username, password, nick_name, enable_flag)
    VALUE (2, 'admin', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '管理员', '1');
-- ----------------------------
-- 插入角色
-- ----------------------------
INSERT INTO sys_role (id, code, name, description)
    VALUE (1, 'user', '用户', '普通用户权限');
INSERT INTO sys_role (id, code, name, description)
    VALUE (2, 'admin', '管理员', '管理员限');
-- ----------------------------
-- 插入 API
-- ----------------------------
INSERT INTO sys_api(id, url, description)
    VALUE (1, '/test/user_url', '管理员测试接口');
INSERT INTO sys_api(id, url, description)
    VALUE (2, '/test/admin_url', '普通用户测试接口');
-- ----------------------------
-- 插入角色权限关系
-- ----------------------------
INSERT INTO sys_role_api (role_id, api_id)
VALUES (1, 2);
INSERT INTO sys_role_api (role_id, api_id)
VALUES (2, 1);
INSERT INTO sys_role_api (role_id, api_id)
VALUES (2, 2);
-- ----------------------------
-- 插入角色用户关系
-- ----------------------------
INSERT INTO sys_role_user (role_id, user_id)
VALUES (1, 1);
INSERT INTO sys_role_user (role_id, user_id)
VALUES (2, 2);
