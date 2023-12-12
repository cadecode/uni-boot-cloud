-- 基本表结构

-- -----------
-- 系统用户表
-- -----------
CREATE TABLE sys_user
(
    id          NUMBER(19),
    username    VARCHAR2(50)  NOT NULL,
    password    VARCHAR2(100) NOT NULL,
    nick_name   VARCHAR2(50)  NOT NULL,
    dept_id     NUMBER(19),
    enable_flag NUMBER(3)     NOT NULL,
    sex         CHAR(1)       null,
    mail        VARCHAR2(50)  null,
    phone       VARCHAR2(50)  null,
    login_ip    VARCHAR2(50)  null,
    login_date  TIMESTAMP(0)  null,

    create_time TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time TIMESTAMP(0) DEFAULT NULL,
    update_user VARCHAR2(100) NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_sys_user_username UNIQUE (username)
)
;

COMMENT ON TABLE sys_user IS '系统用户表';

COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.nick_name IS '昵称';
COMMENT ON COLUMN sys_user.dept_id IS '部门ID';
COMMENT ON COLUMN sys_user.enable_flag IS '是否启用';
comment on column sys_user.sex is '性别';
comment on column sys_user.mail is '邮箱';
comment on column sys_user.phone is '电话';
comment on column sys_user.login_ip is '登录IP';
comment on column sys_user.login_date is '登录时间';

CREATE INDEX idx_sys_user_nick_name ON sys_user (nick_name);
CREATE INDEX idx_sys_user_dept_id ON sys_user (dept_id);
-- -----------
-- 系统角色表
-- -----------
CREATE TABLE sys_role
(
    id          NUMBER(19),
    code        VARCHAR2(50)  NOT NULL,
    name        VARCHAR2(50)  NOT NULL,
    description VARCHAR2(100),
    create_time TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time TIMESTAMP(0) DEFAULT NULL,
    update_user VARCHAR2(100) NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_sys_role_code UNIQUE (code)
)
;

COMMENT ON TABLE sys_role IS '系统角色表';

COMMENT ON COLUMN sys_role.code IS '角色代码';
COMMENT ON COLUMN sys_role.name IS '角色名称';
COMMENT ON COLUMN sys_role.description IS '角色描述';
-- -----------
-- 系统菜单表
-- -----------
CREATE TABLE sys_menu
(
    id             NUMBER(19),
    parent_id      NUMBER(19),
    route_name     VARCHAR2(100) NOT NULL,
    route_path     CLOB          NOT NULL,
    component_path CLOB          NULL,
    menu_name      VARCHAR2(100) NOT NULL,
    leaf_flag      NUMBER(3)     NOT NULL,
    icon           CLOB          NULL,
    order_num      NUMBER(10)    NULL,
    enable_flag    NUMBER(3)     NOT NULL,
    hidden_flag    NUMBER(3)     NOT NULL,
    cache_flag     NUMBER(3)     NOT NULL,

    create_time    TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time    TIMESTAMP(0) DEFAULT NULL,
    update_user    VARCHAR2(100) NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_sys_menu_route_name UNIQUE (route_name)
)
;

COMMENT ON TABLE sys_menu IS '系统菜单表';

COMMENT ON COLUMN sys_menu.route_name IS '路由名称';
COMMENT ON COLUMN sys_menu.route_path IS '路由路径';
COMMENT ON COLUMN sys_menu.component_path IS '组件路径';
COMMENT ON COLUMN sys_menu.menu_name IS '菜单名称';
COMMENT ON COLUMN sys_menu.leaf_flag IS '是否页面';
COMMENT ON COLUMN sys_menu.icon IS '图标';
COMMENT ON COLUMN sys_menu.order_num IS '排序';
COMMENT ON COLUMN sys_menu.enable_flag IS '是否启用';
COMMENT ON COLUMN sys_menu.hidden_flag IS '是否隐藏（内部路由）';
COMMENT ON COLUMN sys_menu.cache_flag IS '是否启用页面缓存';
-- -----------
-- 系统权限表
-- -----------
CREATE TABLE sys_api
(
    id          NUMBER(19),
    url         VARCHAR2(50)  NOT NULL,
    description VARCHAR2(100),
    create_time TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time TIMESTAMP(0) DEFAULT NULL,
    update_user VARCHAR2(100) NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_sys_api_url UNIQUE (url)
)
;

COMMENT ON TABLE sys_api IS '系统权限表';

COMMENT ON COLUMN sys_api.url IS '接口路径';
COMMENT ON COLUMN sys_api.description IS '接口描述';
-- -----------
-- 角色用户关系表
-- -----------
CREATE TABLE sys_role_user
(
    role_id NUMBER(19),
    user_id NUMBER(19),
    PRIMARY KEY (role_id, user_id)
)
;

COMMENT ON TABLE sys_role_user IS '角色用户关系表';

CREATE INDEX idx_sys_role_user_user_id ON sys_role_user (user_id);
-- -----------
-- 角色权限关系表
-- -----------
CREATE TABLE sys_role_api
(
    role_id NUMBER(19),
    api_id  NUMBER(19) NOT NULL,
    PRIMARY KEY (role_id, api_id)
)
;

COMMENT ON TABLE sys_role_api IS '角色权限关系表';

CREATE INDEX idx_sys_role_api_api_id ON sys_role_api (api_id);
-- -----------
-- 角色菜单关系表
-- -----------
CREATE TABLE sys_role_menu
(
    role_id NUMBER(19),
    menu_id NUMBER(19),
    PRIMARY KEY (role_id, menu_id)
)
;

COMMENT ON TABLE sys_role_menu IS '角色菜单关系表';

CREATE INDEX idx_sys_role_menu_api_id ON sys_role_menu (menu_id);
-- -----------
-- 系统字典表
-- -----------
CREATE TABLE sys_dict
(
    id           NUMBER(19),
    name         VARCHAR2(500)  NOT NULL,
    type         VARCHAR2(500)  NOT NULL,
    label        VARCHAR2(500)  NULL,
    value        CLOB           NULL,
    description  VARCHAR2(1000) NULL,
    order_num    NUMBER(10)     NULL,
    default_flag NUMBER(3)      NOT NULL,

    create_time  TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time  TIMESTAMP(0) DEFAULT NULL,
    update_user  VARCHAR2(100)  NULL,
    PRIMARY KEY (id)
)
;

COMMENT ON TABLE sys_dict IS '系统字典表';

COMMENT ON COLUMN sys_dict.name IS '字典名称';
COMMENT ON COLUMN sys_dict.type IS '字典类型';
COMMENT ON COLUMN sys_dict.label IS '字典标签';
COMMENT ON COLUMN sys_dict.value IS '字典值';
COMMENT ON COLUMN sys_dict.description IS '描述';
COMMENT ON COLUMN sys_dict.order_num IS '排序';
COMMENT ON COLUMN sys_dict.default_flag IS '是否默认值';

CREATE INDEX idx_sys_dict_name ON sys_dict (name);
CREATE INDEX idx_sys_dict_type ON sys_dict (type);
-- -----------
-- 系统部门表
-- -----------
CREATE TABLE sys_dept
(
    id          NUMBER(19),
    dept_name   VARCHAR2(50)  NOT NULL,
    parent_id   NUMBER(19)    NULL,
    order_num   NUMBER(10)    NULL,
    leader      VARCHAR2(500) NULL,
    mail        VARCHAR2(50)  null,
    phone       VARCHAR2(50)  null,

    create_time TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time TIMESTAMP(0) DEFAULT NULL,
    update_user VARCHAR2(100) NULL,
    PRIMARY KEY (id)
)
;

COMMENT ON TABLE sys_dept IS '系统部门表';

COMMENT ON COLUMN sys_dept.dept_name IS '部门名';
COMMENT ON COLUMN sys_dept.parent_id IS '父级ID';
COMMENT ON COLUMN sys_dept.order_num IS '排序';
COMMENT ON COLUMN sys_dept.leader IS '领导名';
comment on column sys_dept.mail is '邮箱';
comment on column sys_dept.phone is '电话';

CREATE INDEX idx_sys_dept_dept_name ON sys_dept (dept_name);
CREATE INDEX idx_sys_dept_parent_id ON sys_dept (parent_id);
