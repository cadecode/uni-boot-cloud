# uni-boot-cloud-vue

## 项目简介

1. 本项目是一套通用后台管理系统骨架，前端基于 Vue + ElementUI + VueRouter + Vuex

   基于 [vue-admin-template](https://github.com/PanJiaChen/vue-admin-template) 核心重构（V4.4.0）

   vue-admin-template 是一款管理系统前端模板，基于 MIT 协议开源

2. 本项目采用前后端分离结构，以下是配套后端项目

   [uni-boot-cloud](https://github.com/cadecode/uni-boot-admin)

3. 版本与环境

   @vue/cli: 4.4.4

   vue: 2.6.10

   vuex: 3.1.0

   vue-router: 3.0.6

   element-ui: ^2.15.13

   axios: 0.18.1

   echarts:   ^5.4.1

   sass: 1.26.8

   > node-sass 安装时可能出现失败，参考解决方案：
   >
   > npm install 前设置 npm 镜像、node-sass 镜像
   >
   > npm config set registry https://registry.npm.taobao.org
   > npm config set SASS_BINARY_SITE https://npm.taobao.org/mirrors/node-sass

4. 功能与优势

   基于 [vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)，有丰富的生态（站在巨人的肩膀上）

   开源免费（Apache-2.0 协议）

   较好的编码风格

   支持多环境发布（dev/prod）

   支持动态面包屑

   支持侧边栏自适应收缩

   支持侧边菜单动态生成以及菜单、权限管理

   支持权限路由动态添加，基于后端返回权限菜单

   支持 404 路由

   支持 SVG 图标组件

   集成 Echarts，提供图表组件支持

   集成 Clipboard，提供剪贴板支持


## 功能模块

1. 代码结构

   ```sh
   ├── build
   ├── public
   ├── src
   │   ├── api
   │   ├── asset
   │   ├── component
   │   ├── icon
   │   ├── layout
   │   ├── router
   │   ├── store
   │   ├── style
   │   ├── util
   │   ├── view
   │   ├── main.js
   │   ├── App.vue
   │   └── settings.js
   ├── vue.config.js
   ├── babel.config.js
   ├── jest.config.js
   ├── jsconfig.json
   ├── LICENSE
   ├── package.json
   ├── postcss.config.js
   └── README.md
   ```

2. 目录介绍

   ```sh
   build                  # 构建相关
   public                 # 公共资源
   src                    # 源代码
   src/api                # 接口请求
   src/asset              # 静态资源
   src/component          # 通用组件封装，svg、echarts 等（组件以 Base 开头）
   src/icon               # svg 图标
   src/layout             # 基本布局组件封装（组件以 App 开头）
   src/router             # 路由，实现后端权限菜单转为路由
   src/store              # 状态管理
   src/style              # 公共样式
   src/util               # 公共工具方法
   src/view               # 页面组件，菜单/角色/字典/日志管理等（组件以大写 V 开头）
   src/main.js            # 入口文件
   src/App.vue            # 入口页面
   src/settings.js        # 系统配置项
   vue.config.js          # vue 配置
   babel.config.js        # babel 配置
   package.json           # package.json
   postcss.config.js      # postcss 配置
   ```
