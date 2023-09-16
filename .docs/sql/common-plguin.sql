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

