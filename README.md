# uni-boot-admin

> 基于 SpringBoot 多模块开发的通用后台管理系统骨架
>
> 用于集成各种 Spring 生态中间件，达到练习、备忘的目的

## 框架与开发环境

- Java 版本：8
- Spring 版本：SpringBoot 2.5.2
- 构建工具：Maven 3.6.3

## 代码结构

- common 模块：维护注解、常量类、工具类等
- system 模块：系统管理相关服务与接口
- framework 模块：集成 Security、动态数据源、限流等框架功能
- application 模块：启动类及测试 Demo 接口等

## 计划与进度

- [x] 手写多数据源
- [x] 手写接口全局异常处理和统一返回格式
- [x] 手写 AOP 接口日志
- [x] 集成 Spring Security，手写接口权限控制
- [x] 集成 MybatisPlus
- [x] 集成 Spring Retry
- [x] 集成 p6spy SQL 执行日志
- [x] 手写单机 Redis 锁
- [x] 手写 AOP 限流，基于 Guava
- [x] 集成 Spring Cache
- [x] 集成 dynamic-ds
- [x] 集成 druid 连接池
- [x] 集成 pagehelper
- [x] 集成 Spring Boot Admin 监控
- [x] 集成 TransmittableThreadLocal
- [ ] 集成 dynamicTp
