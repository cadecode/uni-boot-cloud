-- 基本表结构

-- ----------------------------
-- 系统用户表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_user
(
    id          BIGINT UNSIGNED,
    username    VARCHAR(50)  NOT NULL COMMENT '用户名',
    password    VARCHAR(100) NOT NULL COMMENT '密码',
    nick_name   VARCHAR(50)  NOT NULL COMMENT '昵称',
    dept_id BIGINT UNSIGNED COMMENT '部门ID',
    enable_flag TINYINT      NOT NULL COMMENT '是否启用',
    sex         CHAR(1)      null comment '性别',
    mail        VARCHAR(50)  null comment '邮箱',
    phone       VARCHAR(50)  null comment '电话',
    login_ip    VARCHAR(50)  null comment '登录IP',
    login_date  DATETIME     null comment '登录时间',

    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user VARCHAR(100) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    INDEX idx_nick_name (nick_name),
    INDEX idx_dept_id (dept_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '系统用户表';
-- ----------------------------
-- 系统角色表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_role
(
    id          BIGINT UNSIGNED,
    code        VARCHAR(50)  NOT NULL COMMENT '角色代码',
    name        VARCHAR(50)  NOT NULL COMMENT '角色名称',
    type VARCHAR(50) NOT NULL COMMENT '角色类型',
    description VARCHAR(100) COMMENT '角色描述',

    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user VARCHAR(100) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code_type (code, type)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '系统角色表';
-- ----------------------------
-- 系统菜单表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_menu
(
    id             BIGINT UNSIGNED,
    parent_id      BIGINT UNSIGNED,
    route_name     VARCHAR(100) NOT NULL COMMENT '路由名称',
    route_path     TEXT         NOT NULL COMMENT '路由路径',
    component_path TEXT         NULL COMMENT '组件路径',
    menu_name      VARCHAR(100) NOT NULL COMMENT '菜单名称',
    leaf_flag      TINYINT      NOT NULL COMMENT '是否页面',
    icon           TEXT         NULL COMMENT '图标',
    order_num      INT          NULL COMMENT '排序',
    enable_flag    TINYINT      NOT NULL COMMENT '是否启用',
    hidden_flag TINYINT NOT NULL COMMENT '是否隐藏（内部路由）',
    cache_flag  TINYINT NOT NULL COMMENT '是否启用页面缓存',

    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user    VARCHAR(100) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_route_name (route_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '系统菜单表';
-- ----------------------------
-- 系统权限表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_api
(
    id          BIGINT UNSIGNED,
    url         VARCHAR(50)  NOT NULL COMMENT '接口路径',
    description VARCHAR(100) COMMENT '接口描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user VARCHAR(100) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_url (url)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '系统权限表';
-- ----------------------------
-- 角色用户关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_role_user
(
    role_id BIGINT UNSIGNED NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, user_id),
    INDEX idx_user_id (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '角色用户关系表';
-- ----------------------------
-- 角色权限关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_role_api
(
    role_id BIGINT UNSIGNED NOT NULL,
    api_id  BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, api_id),
    INDEX idx_api_id (api_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '角色权限关系表';
-- ----------------------------
-- 角色菜单关系表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_role_menu
(
    role_id BIGINT UNSIGNED NOT NULL,
    menu_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, menu_id),
    INDEX idx_api_id (menu_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '角色菜单关系表';
-- ----------------------------
-- 系统字典表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_dict
(
    id           BIGINT UNSIGNED,
    name         VARCHAR(500)  NOT NULL COMMENT '字典名称',
    type         VARCHAR(500)  NOT NULL COMMENT '字典类型',
    label        VARCHAR(500)  NULL COMMENT '字典标签',
    value        TEXT          NULL COMMENT '字典值',
    description  VARCHAR(1000) NULL COMMENT '描述',
    order_num    INT           NULL COMMENT '排序',
    default_flag TINYINT       NOT NULL COMMENT '是否默认值',

    create_time  DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user  VARCHAR(100)  NULL,
    PRIMARY KEY (id),
    INDEX idx_name (name),
    INDEX idx_type (type)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '系统字典表';
-- ----------------------------
-- 系统部门表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_dept
(
    id          BIGINT UNSIGNED,
    dept_name   VARCHAR(50)     NOT NULL COMMENT '部门名',
    parent_id   BIGINT UNSIGNED NULL COMMENT '父级ID',
    order_num   INT             NULL COMMENT '排序',
    leader      VARCHAR(500)    NULL COMMENT '领导名',
    mail        VARCHAR(50)     null comment '邮箱',
    phone       VARCHAR(50)     null comment '电话',

    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user VARCHAR(100)    NULL,
    PRIMARY KEY (id),
    INDEX idx_dept_name (dept_name),
    INDEX idx_parent_id (parent_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '系统部门表';
