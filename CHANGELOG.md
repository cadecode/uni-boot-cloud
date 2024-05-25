## [2023.1.4](https://github.com/cadecode/uni-boot-cloud-vue/compare/2023.1.3...2023.1.4) (2023-12-01)


### Bug Fixes

* dict update or delete 重新拉取 suggest ([27b8d31](https://github.com/cadecode/uni-boot-cloud-vue/commit/27b8d310a2d094681a1c01feddb67d0fec0d3f79))


### Features

* 查询部门 tree 支持过滤部门名 ([e0742f0](https://github.com/cadecode/uni-boot-cloud-vue/commit/e0742f05824797ce0b407fd639417187423eb419))
* 取消 menu lazy 加载 ([8ad93a2](https://github.com/cadecode/uni-boot-cloud-vue/commit/8ad93a2d5bf43c7776b5afb142e031dd1454ac10))
* 添加部门管理页面 ([4164faf](https://github.com/cadecode/uni-boot-cloud-vue/commit/4164faf469c58e543c9e978180a8d1acd894d800))
* 用户管理支持部门信息的搜索、修改添加 ([f4f4bc0](https://github.com/cadecode/uni-boot-cloud-vue/commit/f4f4bc049ae92a639dd4436fd1a5047e2a857b1f))



## [2023.1.3](https://github.com/cadecode/uni-boot-cloud-vue/compare/2023.1.2...2023.1.3) (2023-11-12)


### Bug Fixes

* 没有出现滚动条时，打开 dialog，头像位置移动 ([dc57ef3](https://github.com/cadecode/uni-boot-cloud-vue/commit/dc57ef33a508156e61925b184d65efdc1c9764a4))
* 选择第一个路由作为 home 时，剔除 hidden 的内部路由 ([c20692a](https://github.com/cadecode/uni-boot-cloud-vue/commit/c20692a01d18d1577dac749e61bfcb9e4639da9e))
* el-autocomplete 没有内容时不弹出建议 ([51ef443](https://github.com/cadecode/uni-boot-cloud-vue/commit/51ef443a56cc2ff819d5de8f4ad3ed91463bb08c))
* json 美化展示 pre 首行缩进问题 ([7d58fba](https://github.com/cadecode/uni-boot-cloud-vue/commit/7d58fbafea8fbdc66f6a7859d337a0d08793d106))
* request 方法提供默认参数，避免编辑器提提示 ([7da0913](https://github.com/cadecode/uni-boot-cloud-vue/commit/7da0913b2dbefa69c8c7fa1d1333745a6bd81f1a))
* route hiddenFlag 改为 hidden ([5ae2485](https://github.com/cadecode/uni-boot-cloud-vue/commit/5ae2485781758777ee06c8588ef0481ae48d6692))


### Features

* 使用 DictLoader 加载字典 ([6fe613a](https://github.com/cadecode/uni-boot-cloud-vue/commit/6fe613aad6b84e7381c6de31ac99e08e65ac6d06))
* 添加 BaseDictLoader 字典组件 ([4fc8b5a](https://github.com/cadecode/uni-boot-cloud-vue/commit/4fc8b5aa3333c61599b9613510ac1bb265865f5e))
* 添加菜单父级ID支持后端查询建议 ([a44d2be](https://github.com/cadecode/uni-boot-cloud-vue/commit/a44d2be83796633e0f2f94caed08faebdc7b4c4a))
* 添加判断角色是否包含在当前用户的方法 ([df53060](https://github.com/cadecode/uni-boot-cloud-vue/commit/df53060fad53018ea1a741c639c2183efa4d2090))
* 添加统一 keep-alive 支持 ([e607890](https://github.com/cadecode/uni-boot-cloud-vue/commit/e607890e09c4e76e10089bc19e43a02d1cbb280b))
* 添加文件上传记录管理页面 ([c7e2eda](https://github.com/cadecode/uni-boot-cloud-vue/commit/c7e2eda18b4e522ba4d4cea5f070f585867ec7b2))
* 消息队列表格展示新增列-连接名称 ([c5de249](https://github.com/cadecode/uni-boot-cloud-vue/commit/c5de24943c76f1118b075f7915591444a50b76f9))
* 引入 vue-frag，在 vue2 中支持 template 多入口 ([93d2b04](https://github.com/cadecode/uni-boot-cloud-vue/commit/93d2b044ad34a05f0f434cbb167665117ff7cee1))
* 支持内部路由的注册与管理 ([3fc7f78](https://github.com/cadecode/uni-boot-cloud-vue/commit/3fc7f78f67e31b7ce0110202f1a606b1fb0517e2))
* menu 添加 cacheFlag 字段 ([91fc25f](https://github.com/cadecode/uni-boot-cloud-vue/commit/91fc25f6d0070633f4802228777ab2fd18221d5b))



## [2023.1.2](https://github.com/cadecode/uni-boot-cloud-vue/compare/2023.1.1...2023.1.2) (2023-08-24)


### Bug Fixes

* 添加对于服务选择框的校验 ([07b88a9](https://github.com/cadecode/uni-boot-cloud-vue/commit/07b88a91cc477909000de207fc0e409ee26f3b46))
* 添加字典 orderNum 的显示 ([263f617](https://github.com/cadecode/uni-boot-cloud-vue/commit/263f61783ac82c38a4672c0517a1a9636be59f0d))
* scope.row 取值错误 ([0f2b8d7](https://github.com/cadecode/uni-boot-cloud-vue/commit/0f2b8d7511a46a88ba67b6b7af59dcca41698ba2))


### Features

* 日志管理列表添加 traceId 字段展示 ([2f5b69e](https://github.com/cadecode/uni-boot-cloud-vue/commit/2f5b69e77bad0d8c5b60a33dd550b3a5db2c778c))
* 日志类型选择框添加清空按钮 ([5d34a40](https://github.com/cadecode/uni-boot-cloud-vue/commit/5d34a401f0575858e9fc4ca664827a46d887be61))
* 搜索按钮设为 primary ([d60f57f](https://github.com/cadecode/uni-boot-cloud-vue/commit/d60f57f3989afb3f5e1308418b9e3103271549fa))
* 添加 API 时根据所选服务提供 url 建议 ([82b80f0](https://github.com/cadecode/uni-boot-cloud-vue/commit/82b80f09b680588ba24d229ef9d53ea9d4a5f648))
* 添加消息队列管理页面 ([1dff389](https://github.com/cadecode/uni-boot-cloud-vue/commit/1dff38910c95de9cc6f53269c76508fefcfec1e7))



## [2023.1.1](https://github.com/cadecode/uni-boot-cloud-vue/compare/2023.1.0...2023.1.1) (2023-08-11)



# [2023.1.0](https://github.com/cadecode/uni-boot-cloud-vue/compare/9a94c99a2ff410a60d67d2c8b06be533cfc60cd6...2023.1.0) (2023-07-24)


### Bug Fixes

* 表达属性映射错误 ([44df191](https://github.com/cadecode/uni-boot-cloud-vue/commit/44df1914654f501e8d6526b5ea95c092a34baa98))
* 操作失败提示类型错误 ([65cf856](https://github.com/cadecode/uni-boot-cloud-vue/commit/65cf856cc39de97d4b2eb3e627d82fe5bc77e43d))
* 调整列宽 ([6a0bd09](https://github.com/cadecode/uni-boot-cloud-vue/commit/6a0bd09033f700b45f337be80944919821760ebd))
* 解决menu.js与router依赖layout冲突 ([ab5cc33](https://github.com/cadecode/uni-boot-cloud-vue/commit/ab5cc3345cc8191fe87a020f349f16671ac50c70))
* 排序字段 input type number ([2ccc3a1](https://github.com/cadecode/uni-boot-cloud-vue/commit/2ccc3a12eefc1e2506e9db09eba2c4d8e9bfc823))
* 取消devServer host ([9085997](https://github.com/cadecode/uni-boot-cloud-vue/commit/9085997def1a580bf6f19b0678e75baf277621e3))
* 使用data变量导致loop render ([8deff6f](https://github.com/cadecode/uni-boot-cloud-vue/commit/8deff6fcbd0800a4177489efa3cbcbe6593d5dd4))
* 是否页面 formatter 不显示 ([20a2bbe](https://github.com/cadecode/uni-boot-cloud-vue/commit/20a2bbe0d9addf6cca76eace46bb684f04777224))
* 是否页面 formatter 不显示 ([2a17e7e](https://github.com/cadecode/uni-boot-cloud-vue/commit/2a17e7e15519f87687de8d2b63883dbea0e209b1))
* 修改路径建议检索规则 ([31e436a](https://github.com/cadecode/uni-boot-cloud-vue/commit/31e436a90b39f285204f89a56e0da9d442b5d82f))
* 引入Nprogress css ([6d16ce4](https://github.com/cadecode/uni-boot-cloud-vue/commit/6d16ce4544f143a77104127fd2107d8281124c54))
* 子级菜单非页面则设置alwaysShow true ([1133f8f](https://github.com/cadecode/uni-boot-cloud-vue/commit/1133f8f6c36309416329ffd9fd0634e70cd31e4d))
* form prop 错误 ([32aab20](https://github.com/cadecode/uni-boot-cloud-vue/commit/32aab20a2aafee2b99136e13ff0b0ddcbbbccdc2))
* store user内容利用cookie持久化 ([c236ce1](https://github.com/cadecode/uni-boot-cloud-vue/commit/c236ce13b9866319cf14c7037c9f2566025fc744))
* updateEnable 请求失败时回退 switch 按钮 ([82dce20](https://github.com/cadecode/uni-boot-cloud-vue/commit/82dce20161f9f6383580337194a63eff8624987f))


### Features

* $refs 引用方式改为. ([30ef1a1](https://github.com/cadecode/uni-boot-cloud-vue/commit/30ef1a14d87834f9b1be7ce6fc06bb4a87f36e13))
* 404返回home改为路由跳转 ([1004bd1](https://github.com/cadecode/uni-boot-cloud-vue/commit/1004bd1e689e288f4844f054bec61efec962aad1))
* 404内容使用中文 ([2c9f9a2](https://github.com/cadecode/uni-boot-cloud-vue/commit/2c9f9a2ddd4acfc86214aab4e7686fbf67bb6f31))
* 版本号调整到 1.0.0 ([2c792ba](https://github.com/cadecode/uni-boot-cloud-vue/commit/2c792ba518a242d1864947578c2ed7723ff1b443))
* 表单数据对象格式优化 ([fa60b52](https://github.com/cadecode/uni-boot-cloud-vue/commit/fa60b521c9f14f61a8c2cb6cefadbe2c226c90a7))
* 表格过长字段使用省略号，show-overflow-tooltip ([2aa6b66](https://github.com/cadecode/uni-boot-cloud-vue/commit/2aa6b666fe47526e2ed5d9ecb730a4a5d5b61417))
* 表格内按钮修改为图标 ([7dda697](https://github.com/cadecode/uni-boot-cloud-vue/commit/7dda697f089d41d115f637c91b3a2361af67d0e2))
* 菜单外部链接追加一个link图标 ([05d2174](https://github.com/cadecode/uni-boot-cloud-vue/commit/05d21743846cba8db1f9ef76fc54db343b44cc69))
* 初始化项目 ([9a94c99](https://github.com/cadecode/uni-boot-cloud-vue/commit/9a94c99a2ff410a60d67d2c8b06be533cfc60cd6))
* 登录后没有任何菜单权限就跳到个人中心 ([39cc4b0](https://github.com/cadecode/uni-boot-cloud-vue/commit/39cc4b018665314c56f313003294a5f7eea694d4))
* 调整按钮页列宽 ([f429324](https://github.com/cadecode/uni-boot-cloud-vue/commit/f429324aa77ad632546dc6ee794cbcf232faf9e2))
* 调整表格列宽 ([532c660](https://github.com/cadecode/uni-boot-cloud-vue/commit/532c660a5d6a347f7d530e81277aa2c4493706d3))
* 封装根据响应内容弹窗提示成功的请求方法 ([27959f5](https://github.com/cadecode/uni-boot-cloud-vue/commit/27959f5feeea35801872172b7b29da24ab0aacc8))
* 个人中心排版格式优化 ([4d85e53](https://github.com/cadecode/uni-boot-cloud-vue/commit/4d85e5321cb26e10a7d461b4691f96f08691cc14))
* 更新用户后从后端刷新用户信息 ([9451da7](https://github.com/cadecode/uni-boot-cloud-vue/commit/9451da722887f88416901536c67f16ce990dc4d9))
* 工具类添加节流防抖函数 ([58ef90d](https://github.com/cadecode/uni-boot-cloud-vue/commit/58ef90d014e703a35c80d8dac5aae88463dea356))
* 挂载ElementUI Message弹窗到vue示例上 ([e4ea944](https://github.com/cadecode/uni-boot-cloud-vue/commit/e4ea944e8d6e35a769365d893ce8cc987c4ce608))
* 角色使用 ElTag 渲染 ([fd05473](https://github.com/cadecode/uni-boot-cloud-vue/commit/fd05473810603ef0cfceb40f2927fd5baa4dbc55))
* 没有头像时使用一个用户图标 ([c07c790](https://github.com/cadecode/uni-boot-cloud-vue/commit/c07c79025d18279ea1895612b94e7e2015c8b51f))
* 日志类型从字典获取 ([611c7e2](https://github.com/cadecode/uni-boot-cloud-vue/commit/611c7e21ee3de2e11b1482b2e01f7a6a6bd9c287))
* 日志异常状态使用 ElTag 标记颜色 ([38eb753](https://github.com/cadecode/uni-boot-cloud-vue/commit/38eb75322f25f8d2eda6f222386844a8b0ea9218))
* 使用 app-container 替换冗余样式 ([395f76b](https://github.com/cadecode/uni-boot-cloud-vue/commit/395f76b8d38f33686c2585f3a6ed46afdfd30da6))
* 使用 ElCol 响应式 ([f5d1843](https://github.com/cadecode/uni-boot-cloud-vue/commit/f5d1843e7901be0d8a8337fff374a0ec86580eb7))
* 使用 ElEmpty 代替空白占位文字 ([f3b8249](https://github.com/cadecode/uni-boot-cloud-vue/commit/f3b82495e4e565c473fa8500ece3880cefd6e1bc))
* 使用meta.noRedirect判断面包屑是否可点击 ([fd9783d](https://github.com/cadecode/uni-boot-cloud-vue/commit/fd9783dc3b5b93eb3e0596eefad3813644b4665a))
* 添加 API 管理 ([45e5c84](https://github.com/cadecode/uni-boot-cloud-vue/commit/45e5c846874dcdc5e130235a0949020d7dbab304))
* 添加 API 时使用 autocomplete ([72f80f9](https://github.com/cadecode/uni-boot-cloud-vue/commit/72f80f9361ced2c17fcfbdb395b8a32ae36aeb3d))
* 添加 Echarts 基础组件 ([aa9ae1d](https://github.com/cadecode/uni-boot-cloud-vue/commit/aa9ae1ddbe590605d60c6ff8ba01d0d1ac16a664))
* 添加 Echarts 示例页面 ([fb16c2a](https://github.com/cadecode/uni-boot-cloud-vue/commit/fb16c2afeb18e42cae3ea7433b88df993368feaa))
* 添加 Home 页的 demo ([86d2d50](https://github.com/cadecode/uni-boot-cloud-vue/commit/86d2d50f6d37b67aa74893eb48e3ee5b016a040d))
* 添加菜单管理基本功能 ([3646c7f](https://github.com/cadecode/uni-boot-cloud-vue/commit/3646c7f13cbc40a9d8558381a79037e17b335e8f))
* 添加登录记住密码功能 ([9a5173c](https://github.com/cadecode/uni-boot-cloud-vue/commit/9a5173c93a5b6299eb0459d8d9f1a733dff918c5))
* 添加非页面的菜单删除逻辑 ([f2f72c7](https://github.com/cadecode/uni-boot-cloud-vue/commit/f2f72c7e60a512d482824a90b22111c565a383a6))
* 添加个人中心页面内容及接口 ([ee91c3f](https://github.com/cadecode/uni-boot-cloud-vue/commit/ee91c3f03cc2c39975737925bb6034e16cab818b))
* 添加个人中心页面用户id展示 ([2449294](https://github.com/cadecode/uni-boot-cloud-vue/commit/2449294e6d3d1cbf34489ceca9d3167842972ed4))
* 添加更新用户enable功能 ([1d4452d](https://github.com/cadecode/uni-boot-cloud-vue/commit/1d4452d5aef59a719e2a0b4a890581ad97e6defc))
* 添加角色管理和菜单、API 绑定 ([4e17431](https://github.com/cadecode/uni-boot-cloud-vue/commit/4e17431dc6d547f8cac7ee72f2087ccb682cd728))
* 添加请求工具自定义提示前缀的参数 ([a165faf](https://github.com/cadecode/uni-boot-cloud-vue/commit/a165fafb020ad32e8e7123fda04f72f497d6f341))
* 添加日志管理页面 ([5c5851e](https://github.com/cadecode/uni-boot-cloud-vue/commit/5c5851e3d83ed52b7714d32001c1c07a024c08c4))
* 添加图标展示页面 ([99e62a7](https://github.com/cadecode/uni-boot-cloud-vue/commit/99e62a7ba60c3300593bb2e8c50468a2ca06b587))
* 添加校验 componentPath ([484d70a](https://github.com/cadecode/uni-boot-cloud-vue/commit/484d70adbd57f6ec9e52e8d0ad947e5fd562cf62))
* 添加一些svg图标 ([6740a32](https://github.com/cadecode/uni-boot-cloud-vue/commit/6740a32fe4d188c37053c5a4fd6bdb1997d58075))
* 添加用户更新、添加、删除功能 ([425ab52](https://github.com/cadecode/uni-boot-cloud-vue/commit/425ab526e5dee777f339f0419a643da763eb7a86))
* 添加用户角色绑定 ([fec5bc8](https://github.com/cadecode/uni-boot-cloud-vue/commit/fec5bc8c11f74f97278b0fcbc9ed407cb1044c43))
* 添加用户中心路由 ([5c08cab](https://github.com/cadecode/uni-boot-cloud-vue/commit/5c08cab9880ae4baac105e049d5b54e6d51bb337))
* 添加字典管理界面和功能 ([eeb5efc](https://github.com/cadecode/uni-boot-cloud-vue/commit/eeb5efc357366c11a2ac406bf73a915d6806d712))
* 添加axios发送query和formdata的工具方法 ([6be0bfd](https://github.com/cadecode/uni-boot-cloud-vue/commit/6be0bfda29f3bc5542839ca61e08b796738917c1))
* 添加svg图标资源 ([4b6c3d9](https://github.com/cadecode/uni-boot-cloud-vue/commit/4b6c3d9f2839def50c0471e809fcb96db39f4643))
* 添加webkit滚动条美化 ([65d41c7](https://github.com/cadecode/uni-boot-cloud-vue/commit/65d41c77432e2799c14faf6c937dab01e890d415))
* 头像后显示昵称 ([0a19492](https://github.com/cadecode/uni-boot-cloud-vue/commit/0a194925c91c99b54a754a89c4202d3764550346))
* 完成菜单树形展示基本功能 ([e685c32](https://github.com/cadecode/uni-boot-cloud-vue/commit/e685c32ece9a4af1939b7834c8d954e2ea53dc9a))
* 完善用户管理基本展示功能 ([aa70b15](https://github.com/cadecode/uni-boot-cloud-vue/commit/aa70b15df37361e31856f50a86b2c890fc1e2da1))
* 文件名修改 ([c7c4d70](https://github.com/cadecode/uni-boot-cloud-vue/commit/c7c4d70e8fd948ebe7f106dc68038232b70ff18f))
* 修改 app-container 全局页面根元素样式 ([eb4ca36](https://github.com/cadecode/uni-boot-cloud-vue/commit/eb4ca3643d31486f087ebe64912f80528ee6d82c))
* 修改 tree check 方法，menu tree 关闭绑定父子级联动 ([55a6a64](https://github.com/cadecode/uni-boot-cloud-vue/commit/55a6a647eb9d65d2885f3eb28f21127917de944b))
* 修改头像下拉框选项 ([e4487ac](https://github.com/cadecode/uni-boot-cloud-vue/commit/e4487ac22721686b15083e265b721b0f54ab1d5a))
* 修改login表单校验 ([8105a76](https://github.com/cadecode/uni-boot-cloud-vue/commit/8105a767d304d2e730ae0108ebb61a97c266d312))
* 修改request异常拦截逻辑 ([7b4b177](https://github.com/cadecode/uni-boot-cloud-vue/commit/7b4b17729b9fbc9974ab6b0c426dba667f843cb1))
* 修改settings auth url ([31ec9ad](https://github.com/cadecode/uni-boot-cloud-vue/commit/31ec9adaa6e7c1ad2e870d2466585f0b072dda29))
* 样式优化 ([b8a3441](https://github.com/cadecode/uni-boot-cloud-vue/commit/b8a3441bad8803c686d7ba5f3088957a00365b1b))
* 样式优化 ([8b10b17](https://github.com/cadecode/uni-boot-cloud-vue/commit/8b10b17791e84498a00e30f26facd1bfe5afa89e))
* 引入剪贴板工具 ([93ecb5a](https://github.com/cadecode/uni-boot-cloud-vue/commit/93ecb5a4d3d71c378249ee6f7ba74ab365601784))
* 优化响应错误提示 ([ddf8de1](https://github.com/cadecode/uni-boot-cloud-vue/commit/ddf8de1b81a3a1476bc49877dc97b8624a166b38))
* 重构登录注销接口 ([79d45b4](https://github.com/cadecode/uni-boot-cloud-vue/commit/79d45b4333bf43b5fc439a5d1d539f0a43c53c65))
* 重构动态路由生成逻辑，由后端mesnu列表构建 ([3a63f54](https://github.com/cadecode/uni-boot-cloud-vue/commit/3a63f54a91be7d37cbbbac3e9b13683a9f3ab3ad))
* 重构路由权限认证逻辑 ([7b83150](https://github.com/cadecode/uni-boot-cloud-vue/commit/7b83150b49b9aa96a8111ee1b5fe0c367c85549a))
* 重构axios工具类 ([eaac5a2](https://github.com/cadecode/uni-boot-cloud-vue/commit/eaac5a2d80cbe14e46d438242661c7ee7369e2c2))
* ElTree 非懒加载时不需要指定 isLeaf，根据 children 渲染 ([0778add](https://github.com/cadecode/uni-boot-cloud-vue/commit/0778add4f890c933cc63fff131432dc835992e50))
* leafFlag 不可更新 ([5fa2604](https://github.com/cadecode/uni-boot-cloud-vue/commit/5fa260472ffa51439e39ddacb0317818af45dd03))
* log列表支持时间范围过滤 ([dde7f09](https://github.com/cadecode/uni-boot-cloud-vue/commit/dde7f09df0c28d47cd9f9a37b4d3e02369e1ae4f))
* login表单title从settings取配置 ([66bb369](https://github.com/cadecode/uni-boot-cloud-vue/commit/66bb3696b287e9645c764c3e2b64489517590272))



