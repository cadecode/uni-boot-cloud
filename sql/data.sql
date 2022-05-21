-- ----------------------------
-- 插入用户
-- 密码使用 BCryptPasswordEncoder 生成
-- ----------------------------
INSERT INTO security_user (username, password, nick_name, enable_flag)
    VALUE ('user', '$2a$10$mQJ9z.l0FW.pUSJ0BnrkjeMHrGJs96oGY3mzB1HZGSAvFgHoo2k1O', '用户', '1');
INSERT INTO security_user (username, password, nick_name, enable_flag)
    VALUE ('admin', '$2a$10$uSg7P6ESwIbAalJIrPlc1uJ7szowzH/tUZsino2s3Bxz/ieqgCygm', '管理员', '1');
-- ----------------------------
-- 插入角色
-- ----------------------------
INSERT INTO security_role (code, name, description)
    VALUE ('user', '用户', '普通用户权限');
INSERT INTO security_role (code, name, description)
    VALUE ('admin', '管理员', '管理员限');
-- ----------------------------
-- 插入 API
-- ----------------------------
INSERT INTO security_api(url, description)
    VALUE ('/api/demo/admin', '管理员测试接口');
INSERT INTO security_api(url, description)
    VALUE ('/api/demo/user', '普通用户测试接口');
-- ----------------------------
-- 插入角色权限关系
-- ----------------------------
INSERT INTO security_role_api (role_id, api_id)
VALUES (1, 2);
INSERT INTO security_role_api (role_id, api_id)
VALUES (2, 1);
INSERT INTO security_role_api (role_id, api_id)
VALUES (2, 2);
-- ----------------------------
-- 插入角色用户关系
-- ----------------------------
INSERT INTO security_role_user (role_id, user_id)
VALUES (1, 1);
INSERT INTO security_role_user (role_id, user_id)
VALUES (2, 2);
