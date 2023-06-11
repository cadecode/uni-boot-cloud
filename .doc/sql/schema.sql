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
    UNIQUE KEY uk_username (username)
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
    description VARCHAR(100) COMMENT '角色描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user VARCHAR(100) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code)
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
-- 系统日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS sys_log
(
    id             BIGINT UNSIGNED,
    log_type       VARCHAR(50)   NOT NULL COMMENT 'log 类型',
    url            VARCHAR(500)  NULL COMMENT 'URL',
    exceptional    TINYINT(1)    NOT NULL COMMENT '是否异常',
    access_user    VARCHAR(100)  NULL COMMENT '访问者',
    description    VARCHAR(1000) NULL COMMENT '描述',
    class_method   VARCHAR(500)  NOT NULL COMMENT '方法名',
    thread_id      VARCHAR(100)  NULL COMMENT '线程 ID',
    thread_name    VARCHAR(500)  NULL COMMENT '线程名',
    ip             VARCHAR(50)   NULL COMMENT 'IP',
    http_method    VARCHAR(50)   NULL COMMENT 'HTTP 方法',
    request_params TEXT          NULL COMMENT '参数',
    result         TEXT          NULL COMMENT '结果',
    time_cost      BIGINT        NULL COMMENT '接口耗时',
    os             VARCHAR(200)  NULL COMMENT '操作系统',
    browser        VARCHAR(200)  NULL COMMENT '浏览器',
    user_agent     VARCHAR(500)  NULL COMMENT 'user-agent',

    create_time    DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time    DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user    VARCHAR(100)  NULL,
    PRIMARY KEY (id),
    INDEX idx_create_time (create_time),
    INDEX idx_log_type (log_type),
    INDEX idx_url (url),
    INDEX idx_access_user (access_user)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '系统日志表';
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
