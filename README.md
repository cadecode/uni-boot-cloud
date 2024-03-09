# uni-boot-admin

> 基于 SpringCloud 的通用后台管理系统骨架，引入 Nacos、OpenFeign、Gateway 等微服务核心组件
>
> 基于 SpringBoot 生态，集成 SpringSecurity、Druid、MyBatisPlus、多数据源、二级缓存、DynamicTP 等常用组件

## 项目简介

1. 本项目是一套通用后台管理系统骨架，后端基于 SpringCloud 生态，集成常用组件，方便扩展使用

2. 本项目采用前后端分离结构，以下是配套前端项目

   [uni-boot-admin-vue](https://github.com/cadecode/uni-boot-admin-vue) 基于 Vue + ElementUI

3. 版本与环境

   JDK 版本：8

   SpringBoot 版本：SpringBoot 2.5.4

   SpringCloud 版本：SpringCloud 2020.0.5、SpringCloudAlibaba 2021.1

   数据库版本：MySQL8

   依赖管理工具：Maven 3.6.3

   开发工具：IDEA

4. 功能与优势

   开源免费（Apache-2.0 协议）

   较好的编码风格

   基于 SpringCloud、SpringCloudAlibaba 提供微服务生态支持

   基于 SpringSecurity 实现认证授权，支持 API 级的角色访问控制，可动态配置 API 权限、菜单权限

   权限认证支持本地 JWT 认证和 Redis Token 认证模式

   支持全局异常处理、全局统一接口返回格式（提供声明式注解）

   支持 AOP 接口日志（提供声明式注解）

   支持接口或方法限流，基于 Guava（提供声明式注解）

   支持基于 RedisTemplate 的 Redis 分布式锁（ScheduledExecutorService + 看门狗）

   集成数据库访问，Druid + MybatisPlus + PageHelper

   集成多数据源，提供原生方案和第三方框架 dynamic-datasource

   集成 DynamicTP，提供动态线程池支持

   集成 SpringBoot Cache、Caffeine、Redis，提供二级缓存及缓存管理器支持

   集成 knife4j，提供 swagger module 的动态配置支持

   集成 RabbitMQ，提供通过配置声明多数据源、交换机和队列功能，以及事务消息、消息重试的支持

   集成 x-file-storage，提供多平台文件存储、下载的支持

   集成 xxl-job、PowerJob，提供分布式定时任务调度支持

   集成 sharding jdbc，提供分库分表、读写分离支持

   ...

## 功能模块

1. 代码结构

   ```sh
   ├── common
   │   ├── core
   │   ├── plugin
   │   │   ├── actuator
   │   │   ├── cache
   │   │   ├── concurrent
   │   │   ├── datasource
   │   │   ├── log
   │   │   ├── mybatis
   │   │   ├── swagger
   │   │   ├── mq
   │   │   ├── storage
   │   │   ├── job
   │   │   └── pom.xml
   │   └── pom.xml
   ├── dependencies
   ├── framework
   │   ├── framework_api
   │   ├── framework_base
   │   ├── framework_svc
   ├── example
   │   ├── example_api
   │   ├── example_svc
   ├── gateway
   ├── LICENSE
   ├── pom.xml
   └── README.md
   ```

2. 目录介绍

   ```sh
   common                      # 通用模块
   common/core                 # 通用核心模块，包含公共注解、常量、异常、工具类、抽象模板等
   common/plugin               # 通用插件模块，用于抽离各种组件、第三方库
   common/plugin/actuator      # 系统资源监控模块，集成 SpringBoot Admin
   common/plugin/cache         # 缓存模块，集成 caffeine、Redis、二级缓存
   common/plugin/concurrent    # 并发模块，集成 DynamicTP 线程池、阿里 Ttl
   common/plugin/datasource    # 数据源模块，集成 Druid、多数据源、ShardingJdbc
   common/plugin/log           # 日志模块，集成 API 访问日志
   common/plugin/mybatis       # mybatis 模块，集成 MybatisPlus、PageHelper
   common/plugin/swagger       # swagger 模块，集成 knife4j，动态配置 swagger 的 module
   common/plugin/mq            # mq 模块，集成 RabbitMQ，提供事务消息、消息重试的支持
   common/plugin/storage       # storage 模块，集成 x-file-storage
   common/plugin/job           # job 模块，集成 xxl-job
   dependencies                # 依赖管理模块，作为父模块，管理全局依赖版本
   framework                   # 框架模块
   framework/framework_api     # 框架 feign api
   framework/framework_base    # 框架通用配置
   framework/framework_svc     # 框架服务，提供基础功能支持，如用户/菜单/角色/字典等管理功能等
   example                     # 示例模块
   example/example_api         # 示例 feign api
   example/example_svc         # 示例服务
   gateway                     # 网关服务
   ```

## 演示截图

参见配套前端项目

[uni-boot-admin-vue](https://github.com/cadecode/uni-boot-admin-vue)
