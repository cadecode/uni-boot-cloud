-- -----------
-- 通用日志表
-- -----------
CREATE TABLE plg_log
(
    id             NUMBER(19),
    log_type       VARCHAR2(50)   NOT NULL,
    url            VARCHAR2(500)  NULL,
    exceptional    NUMBER(3)      NOT NULL,
    access_user    VARCHAR2(100)  NULL,
    description    VARCHAR2(1000) NULL,
    class_method   VARCHAR2(500)  NOT NULL,
    thread_id      VARCHAR2(100)  NULL,
    thread_name    VARCHAR2(500)  NULL,
    ip             VARCHAR2(50)   NULL,
    http_method    VARCHAR2(50)   NULL,
    request_params CLOB           NULL,
    result         CLOB           NULL,
    time_cost      NUMBER(19)     NULL,
    os             VARCHAR2(200)  NULL,
    browser        VARCHAR2(200)  NULL,
    user_agent     VARCHAR2(500)  NULL,
    trace_id       VARCHAR2(100)  NULL,

    create_time    TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time    TIMESTAMP(0) DEFAULT NULL,
    update_user    VARCHAR2(100)  NULL,
    PRIMARY KEY (id)
)
;

COMMENT ON TABLE plg_log IS '通用日志表';

COMMENT ON COLUMN plg_log.log_type IS 'log 类型';
COMMENT ON COLUMN plg_log.url IS 'URL';
COMMENT ON COLUMN plg_log.exceptional IS '是否异常';
COMMENT ON COLUMN plg_log.access_user IS '访问者';
COMMENT ON COLUMN plg_log.description IS '描述';
COMMENT ON COLUMN plg_log.class_method IS '方法名';
COMMENT ON COLUMN plg_log.thread_id IS '线程 ID';
COMMENT ON COLUMN plg_log.thread_name IS '线程名';
COMMENT ON COLUMN plg_log.ip IS 'IP';
COMMENT ON COLUMN plg_log.http_method IS 'HTTP 方法';
COMMENT ON COLUMN plg_log.request_params IS '参数';
COMMENT ON COLUMN plg_log.result IS '结果';
COMMENT ON COLUMN plg_log.time_cost IS '接口耗时';
COMMENT ON COLUMN plg_log.os IS '操作系统';
COMMENT ON COLUMN plg_log.browser IS '浏览器';
COMMENT ON COLUMN plg_log.user_agent IS 'user-agent';
COMMENT ON COLUMN plg_log.trace_id IS 'trace-id';

CREATE INDEX idx_plg_log_create_time ON plg_log (create_time);
CREATE INDEX idx_plg_log_log_type ON plg_log (log_type);
CREATE INDEX idx_plg_log_url ON plg_log (url);
CREATE INDEX idx_plg_log_access_user ON plg_log (access_user);

-- -----------
-- 消息记录表
-- -----------
CREATE TABLE plg_mq_msg
(
    id                    NUMBER(19),
    biz_type              VARCHAR2(200)                                NULL,
    biz_key               VARCHAR2(200)                                NULL,
    exchange              VARCHAR2(200)                                NOT NULL,
    routing_key           VARCHAR2(200)                                NOT NULL,
    connection_name       VARCHAR2(200)                                NULL,
    message               CLOB                                         NOT NULL,
    send_state            VARCHAR2(100)                                NULL,
    consume_state         VARCHAR2(100)                                NULL,
    next_retry_time       TIMESTAMP(0)                                 NULL,
    cause                 CLOB                                         NULL,
    left_retry_times      NUMBER(10)                                   NULL,
    max_retry_times       NUMBER(10)                                   NULL,
    backoff_init_interval NUMBER(19) CHECK (backoff_init_interval > 0) NULL,
    backoff_multiplier    BINARY_DOUBLE                                NULL,
    backoff_max_interval  NUMBER(19) CHECK (backoff_max_interval > 0)  NULL,

    create_time           TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time           TIMESTAMP(0) DEFAULT NULL,
    update_user           VARCHAR2(100)                                NULL,
    PRIMARY KEY (id)
)
;

COMMENT ON TABLE plg_mq_msg IS '消息记录表';

COMMENT ON COLUMN plg_mq_msg.biz_type IS '业务类型';
COMMENT ON COLUMN plg_mq_msg.biz_key IS '业务键';
COMMENT ON COLUMN plg_mq_msg.exchange IS '交换机';
COMMENT ON COLUMN plg_mq_msg.routing_key IS '路由 key';
COMMENT ON COLUMN plg_mq_msg.connection_name IS '连接名称';
COMMENT ON COLUMN plg_mq_msg.message IS '消息内容';
COMMENT ON COLUMN plg_mq_msg.send_state IS '发送方状态';
COMMENT ON COLUMN plg_mq_msg.consume_state IS '消费方状态';
COMMENT ON COLUMN plg_mq_msg.next_retry_time IS '下次重试时间';
COMMENT ON COLUMN plg_mq_msg.cause IS '重试原因';
COMMENT ON COLUMN plg_mq_msg.left_retry_times IS '剩余重试次数';
COMMENT ON COLUMN plg_mq_msg.max_retry_times IS '最大重试次数';
COMMENT ON COLUMN plg_mq_msg.backoff_init_interval IS '退避-初始时间间隔';
COMMENT ON COLUMN plg_mq_msg.backoff_multiplier IS '退避-乘子';
COMMENT ON COLUMN plg_mq_msg.backoff_max_interval IS '退避-最大时间间隔';

CREATE INDEX idx_plg_mq_msg_create_time ON plg_mq_msg (create_time, send_state);
CREATE INDEX idx_plg_mq_msg_next_retry_time ON plg_mq_msg (next_retry_time, send_state, left_retry_times);
CREATE INDEX idx_plg_mq_msg_biz_type ON plg_mq_msg (biz_type);
CREATE INDEX idx_plg_mq_msg_send_state ON plg_mq_msg (send_state);

-- -----------
-- 文件记录表
-- -----------
CREATE TABLE plg_file
(
    id                NUMBER(19),
    url               varchar2(512) NOT NULL,
    "SIZE"            number(19)    NULL,
    filename          varchar2(256) NULL,
    original_filename varchar2(256) NULL,
    base_path         varchar2(256) NULL,
    path              varchar2(256) NULL,
    ext               varchar2(32)  NULL,
    content_type      varchar2(128) NULL,
    platform          varchar2(32)  NULL,
    th_url            varchar2(512) NULL,
    th_filename       varchar2(256) NULL,
    th_size           number(19)    NULL,
    th_content_type   varchar2(128) NULL,
    object_id         varchar2(32)  NULL,
    object_type       varchar2(32)  NULL,
    metadata          clob          NULL,
    user_metadata     clob          NULL,
    th_metadata       clob          NULL,
    th_user_metadata  clob          NULL,
    attr              clob          NULL,
    file_acl          varchar2(32)  NULL,
    th_file_acl       varchar2(32)  NULL,

    create_time       TIMESTAMP(0) DEFAULT SYSTIMESTAMP,
    update_time       TIMESTAMP(0) DEFAULT NULL,
    update_user       VARCHAR2(100) NULL,
    PRIMARY KEY (id)
)
;

COMMENT ON TABLE plg_file IS '文件记录表';

COMMENT ON COLUMN plg_file.url IS '文件访问地址';
COMMENT ON COLUMN plg_file."SIZE" IS '文件大小，单位字节';
COMMENT ON COLUMN plg_file.filename IS '文件名称';
COMMENT ON COLUMN plg_file.original_filename IS '原始文件名';
COMMENT ON COLUMN plg_file.base_path IS '基础存储路径';
COMMENT ON COLUMN plg_file.path IS '存储路径';
COMMENT ON COLUMN plg_file.ext IS '文件扩展名';
COMMENT ON COLUMN plg_file.content_type IS 'MIME类型';
COMMENT ON COLUMN plg_file.platform IS '存储平台';
COMMENT ON COLUMN plg_file.th_url IS '缩略图访问路径';
COMMENT ON COLUMN plg_file.th_filename IS '缩略图名称';
COMMENT ON COLUMN plg_file.th_size IS '缩略图大小，单位字节';
COMMENT ON COLUMN plg_file.th_content_type IS '缩略图MIME类型';
COMMENT ON COLUMN plg_file.object_id IS '文件所属对象id';
COMMENT ON COLUMN plg_file.object_type IS '文件所属对象类型，例如用户头像，评价图片';
COMMENT ON COLUMN plg_file.metadata IS '文件元数据';
COMMENT ON COLUMN plg_file.user_metadata IS '文件用户元数据';
COMMENT ON COLUMN plg_file.th_metadata IS '缩略图元数据';
COMMENT ON COLUMN plg_file.th_user_metadata IS '缩略图用户元数据';
COMMENT ON COLUMN plg_file.attr IS '附加属性';
COMMENT ON COLUMN plg_file.file_acl IS '文件ACL';
COMMENT ON COLUMN plg_file.th_file_acl IS '缩略图文件ACL';

CREATE INDEX idx_plg_file_url ON plg_file (url);
CREATE INDEX idx_plg_file_filename ON plg_file (filename);
CREATE INDEX idx_plg_file_platform_url ON plg_file (platform, url);
CREATE INDEX idx_plg_file_object_id ON plg_file (object_id);
CREATE INDEX idx_plg_file_object_type_id ON plg_file (object_type, object_id);
