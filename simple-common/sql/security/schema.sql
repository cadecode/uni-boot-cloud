-- ----------------------------
-- 系统用户表
-- ----------------------------
create table security_user
(
    id          bigint unsigned auto_increment,
    username    varchar(50)      not null comment '用户名',
    password    varchar(100)     not null comment '密码',
    nick_name   varchar(50)      not null comment '昵称',
    enable_flag tinyint unsigned not null comment '是否启用',
    token       char(36) comment 'token',
    token_time  datetime comment 'token 时间',
    create_time datetime default current_timestamp,
    update_time datetime default null on update current_timestamp,
    primary key (id),
    constraint uk_username unique (username)
) engine = InnoDB
  default charset = utf8
    comment '系统用户表';
-- ----------------------------
-- 系统角色表
-- ----------------------------
create table security_role
(
    id          bigint unsigned auto_increment,
    code        varchar(50) not null comment '角色代码',
    name        varchar(50) not null comment '角色名称',
    description varchar(100) comment '角色描述',
    create_time datetime default current_timestamp,
    update_time datetime default null on update current_timestamp,
    primary key (id),
    constraint uk_code unique (code)
) engine = InnoDB
  default charset = utf8
    comment '系统角色表';
-- ----------------------------
-- 系统权限表
-- ----------------------------
create table security_api
(
    id          bigint unsigned auto_increment,
    url         varchar(50) not null comment '接口路径',
    description varchar(100) comment '接口描述',
    create_time datetime default current_timestamp,
    update_time datetime default null on update current_timestamp,
    primary key (id),
    constraint uk_url unique (url)
) engine = InnoDB
  default charset = utf8
    comment '系统权限表';
-- ----------------------------
-- 角色用户关系表
-- ----------------------------
create table security_role_user
(
    role_id bigint unsigned not null,
    user_id bigint unsigned not null
) engine = InnoDB
  default charset = utf8
    comment '角色用户关系表';
-- ----------------------------
-- 角色权限关系表
-- ----------------------------
create table security_role_api
(
    role_id bigint unsigned not null,
    api_id  bigint unsigned not null
) engine = InnoDB
  default charset = utf8
    comment '角色权限关系表';
