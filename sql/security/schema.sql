-- ----------------------------
-- 系统用户表
-- ----------------------------
CREATE TABLE IF NOT EXISTS security_user
(
    id            BIGINT UNSIGNED AUTO_INCREMENT,
    username      VARCHAR(50)      NOT NULL COMMENT '用户名',
    password      VARCHAR(100)     NOT NULL COMMENT '密码',
    nick_name     VARCHAR(50)      NOT NULL COMMENT '昵称',
    enable_flag   TINYINT UNSIGNED NOT NULL COMMENT '是否启用',
    refresh_token CHAR(36) COMMENT 'token',
    token_time    TIMESTAMP COMMENT 'token 时间',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time   TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
    COMMENT '系统用户表';
-- ----------------------------
-- 系统角色表
-- ----------------------------
CREATE TABLE IF NOT EXISTS security_role
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    code        VARCHAR(50) NOT NULL COMMENT '角色代码',
    name        VARCHAR(50) NOT NULL COMMENT '角色名称',
    description VARCHAR(100) COMMENT '角色描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
    COMMENT '系统角色表';
-- ----------------------------
-- 系统权限表
-- ----------------------------
CREATE TABLE IF NOT EXISTS security_api
(
    id          BIGINT UNSIGNED AUTO_INCREMENT,
    url         VARCHAR(50) NOT NULL COMMENT '接口路径',
    description VARCHAR(100) COMMENT '接口描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_url (url)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
    COMMENT '系统权限表';
-- ----------------------------
-- 角色用户关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS security_role_user
(
    role_id BIGINT UNSIGNED NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, user_id),
    INDEX idx_user_id (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
    COMMENT '角色用户关系表';
-- ----------------------------
-- 角色权限关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS security_role_api
(
    role_id BIGINT UNSIGNED NOT NULL,
    api_id  BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, api_id),
    INDEX idx_api_id (api_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
    COMMENT '角色权限关系表';
