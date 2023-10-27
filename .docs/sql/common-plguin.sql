-- ----------------------------
-- 通用日志表
-- ----------------------------
CREATE TABLE IF NOT EXISTS plg_log
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
    trace_id       VARCHAR(100)  NULL COMMENT 'trace-id',

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
    COMMENT '通用日志表';

-- ----------------------------
-- 消息记录表
-- ----------------------------
CREATE TABLE IF NOT EXISTS plg_mq_msg
(
    id                    BIGINT UNSIGNED,
    biz_type              VARCHAR(200)    NULL COMMENT '业务类型',
    biz_key               VARCHAR(200)    NULL COMMENT '业务键',
    exchange              VARCHAR(200)    NOT NULL COMMENT '交换机',
    routing_key           VARCHAR(200)    NOT NULL COMMENT '路由 key',
    connection_name       VARCHAR(200)    NULL COMMENT '连接名称',
    message               TEXT            NOT NULL COMMENT '消息内容',
    send_state            VARCHAR(100)    NULL COMMENT '发送方状态',
    consume_state         VARCHAR(100)    NULL COMMENT '消费方状态',
    next_retry_time       DATETIME        NULL COMMENT '下次重试时间',
    cause                 TEXT            NULL COMMENT '重试原因',
    left_retry_times      INT             NULL COMMENT '剩余重试次数',
    max_retry_times       INT             NULL COMMENT '最大重试次数',
    backoff_init_interval BIGINT UNSIGNED NULL COMMENT '退避-初始时间间隔',
    backoff_multiplier    DOUBLE          NULL COMMENT '退避-乘子',
    backoff_max_interval  BIGINT UNSIGNED NULL COMMENT '退避-最大时间间隔',

    create_time           DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time           DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user           VARCHAR(100)    NULL,
    PRIMARY KEY (id),
    INDEX idx_create_time (create_time, send_state),
    INDEX idx_next_retry_time (next_retry_time, send_state, left_retry_times),
    INDEX idx_biz_type (biz_type),
    INDEX idx_send_state (send_state)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '消息记录表';

-- ----------------------------
-- 文件记录表
-- ----------------------------
CREATE TABLE plg_file
(
    id                BIGINT UNSIGNED,
    url               varchar(512) NOT NULL COMMENT '文件访问地址',
    size              bigint(20)   NULL COMMENT '文件大小，单位字节',
    filename          varchar(256) NULL COMMENT '文件名称',
    original_filename varchar(256) NULL COMMENT '原始文件名',
    base_path         varchar(256) NULL COMMENT '基础存储路径',
    path              varchar(256) NULL COMMENT '存储路径',
    ext               varchar(32)  NULL COMMENT '文件扩展名',
    content_type      varchar(128) NULL COMMENT 'MIME类型',
    platform          varchar(32)  NULL COMMENT '存储平台',
    th_url            varchar(512) NULL COMMENT '缩略图访问路径',
    th_filename       varchar(256) NULL COMMENT '缩略图名称',
    th_size           bigint(20)   NULL COMMENT '缩略图大小，单位字节',
    th_content_type   varchar(128) NULL COMMENT '缩略图MIME类型',
    object_id         varchar(32)  NULL COMMENT '文件所属对象id',
    object_type       varchar(32)  NULL COMMENT '文件所属对象类型，例如用户头像，评价图片',
    metadata          text         NULL COMMENT '文件元数据',
    user_metadata     text         NULL COMMENT '文件用户元数据',
    th_metadata       text         NULL COMMENT '缩略图元数据',
    th_user_metadata  text         NULL COMMENT '缩略图用户元数据',
    attr              text         NULL COMMENT '附加属性',
    file_acl          varchar(32)  NULL COMMENT '文件ACL',
    th_file_acl       varchar(32)  NULL COMMENT '缩略图文件ACL',

    create_time       DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time       DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    update_user       VARCHAR(100) NULL,
    PRIMARY KEY (id),
    INDEX idx_url (url),
    INDEX idx_filename (filename),
    INDEX idx_platform_url (platform, url),
    INDEX idx_object_id (object_id),
    INDEX idx_object_type_id (object_type, object_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT '文件记录表';
