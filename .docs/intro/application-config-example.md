## 服务参考配置

> 服务配置文件推荐由 Nacos 统一管理

### 服务共享配置

shared-config.yml

```yaml
server:
  shutdown: graceful

# spring config
spring:
  lifecycle:
    timeout-per-shutdown-phase: 60s
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  boot:
    admin:
      client:
        url: http://127.0.0.1:1111
  # redis 公共配置
  redis:
    lettuce:
      pool:
        max-active: 8
        min-idle: 0
        max-idle: 8
        max-wait: -1
  # 数据源公共配置
  datasource:
    druid:
      initialSize: 5
      minIdle: 10
      maxActive: 20
      maxWait: 60000
      timeBetweenEvictionRunsMillis: 60000
      minEvictableIdleTimeMillis: 300000
      maxEvictableIdleTimeMillis: 900000
      validationQuery: SELECT 1 FROM DUAL
      testWhileIdle: true
      testOnBorrow: false
      testOnReturn: false
      webStatFilter:
        enabled: true
      statViewServlet:
        enabled: true
        allow:
        url-pattern: /druid/*
        login-username: dev
        login-password: dev123
      filter:
        stat:
          enabled: true
          log-slow-sql: true
          slow-sql-millis: 1000
          merge-sql: false
        wall:
          config:
            multi-statement-allow: true
  dynamic:
    tp:
      collectorTypes: logging
      logPath: ${logging.file.path}
      monitorInterval: 10
      executors:
        - threadPoolName: asyncExecutor
          thread-name-prefix: asyncExecutor-
          corePoolSize: 6
          maximumPoolSize: 8
          queueCapacity: 1000
          queueType: VariableLinkedBlockingQueue
          rejectedHandlerType: CallerRunsPolicy
          keepAliveTime: 50
          waitForTasksToCompleteOnShutdown: true
          awaitTerminationSeconds: 300
          notifyEnabled: false
          taskWrapperNames: [ "ttl", "mdc" ]

# pagehelper
pagehelper:
  reasonable: true
  supportMethodsArguments: true
  params: count=countSql
  auto-dialect: true
  auto-runtime-dialect: true
  page-size-zero: true

# mybatis plus 配置
mybatis-plus:
  mapper-locations: classpath*:mapper/**/*.xml
  type-aliases-package: com.github.cadecode.**.bean,com.github.cadecode.**.mybatis.converter
  type-enums-package: com.github.cadecode.**.enums
  configuration:
    map-underscore-to-camel-case: true

# swagger配置
knife4j:
  enable: true
  basic:
    enable: true
    username: dev
    password: dev123

# actuator
management:
  endpoints:
    web:
      exposure:
        include: '*'

# uni-boot
uni-boot:
  swagger:
    enable: true
    version: ${uni-boot.config.version}
    name: Cade Li
    url: https://github.com/cadecode/uni-boot-admin
    email: cadecode@foxmail.com
    module:
      default: com.github.cadecode.uniboot
  security:
    auth-model: redis
    token-config:
      expiration: 86400
      secret: 12345678123456781234567812345678
  cache:
    dl:
      allow-null-values: true
```

shared-config-dev.yml（DEV 环境）

```yaml
spring:
  cloud:
    inetutils:
      # 多网卡机器上指定 ip 偏好
      preferred-networks:
        - 192.168.0.
        - 192.168.238.
        - 10.

# 关闭 swagger 认证
knife4j:
  basic:
    enable: false
```

### framework 服务配置

uni-boot-framework-dev.yml（DEV 环境）

```yaml
server:
  port: 8001

spring:
  redis:
    host: localhost
    port: 6379
    password: ENC(jIiKGruIMgDkKx5wj2gJRCROqPutkTvZ)
    database: 0
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:p6spy:mysql://localhost:3306/uni_boot_dev?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
          username: root
          password: ENC(donQ3DazAc4/6B+NFSRdxw==)
          driver-class-name: com.p6spy.engine.spy.P6SpyDriver

# uni-boot 配置
uni-boot:
  swagger:
    title: Framework API Docs
    description: Framework 服务在线文档 By Swagger
  security:
    ignore-urls: /common/download**
  framework:
    file-base-path: D:/uniboot/file/temp/
```

### example 服务配置

uni-boot-example-dev.yml（DEV 环境）

```yaml
server:
  port: 8010

spring:
  redis:
    host: localhost
    port: 6379
    password: ENC(jIiKGruIMgDkKx5wj2gJRCROqPutkTvZ)
    database: 0
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    dynamic:
      primary: master
      datasource:
        master:
          url: jdbc:p6spy:mysql://localhost:3306/uni_boot_dev_example?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
          username: root
          password: ENC(donQ3DazAc4/6B+NFSRdxw==)
          driver-class-name: com.p6spy.engine.spy.P6SpyDriver

# uni-boot 配置
uni-boot:
  swagger:
    title: Example API Docs
    description: Example 服务在线文档 By Swagger
  security:
    ignore-urls:
```

### gateway 服务配置

uni-boot-gateway-dev.yml（DEV 环境）

```yaml
server:
  port: 8000

spring:
  cloud:
    gateway:
      routes:
        - id: uni-boot-framework
          uri: lb://uni-boot-framework
          predicates:
            - Path=/framework/**
          filters:
            - StripPrefix=1
        - id: uni-boot-example
          uri: lb://uni-boot-example
          predicates:
            - Path=/example/**
          filters:
            - StripPrefix=1
```
