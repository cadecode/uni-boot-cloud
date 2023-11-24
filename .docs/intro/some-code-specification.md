## 一些代码规约

### 常见命名规范

web 接口，以 Controller 结尾，放于 controller 包

service，以 Service 结尾，放于 service 包

service 实现，以 ServiceImpl 结尾，放于 serviceimpl 包

dao，以 Mapper 结尾，放于 mapper 包

manager（复杂业务聚合处理），以 Manager 结尾，放于 manager 包

feign 客户端，以 Client 结尾，放于 feignclient 包

bean 转换器，以 Convert 结尾，放于 convert 包

aop 切面，以 Aspect 结尾，放于 aspect 包

常量类，以 Const 结尾，放于 consts 包

枚举类，以 Enum 结尾，放于 enums 包

### bean 的命名

在 bean 包下维护各类简单 bean（贫血模型）

数据表实体，类名和表名一致，放于 bean.po 或 bean.entity 包

web 请求/返回实体，以 ReqVo/ResVo 结尾，放于 bean.vo 包

feign 请求/返回实体，以 ReqDto/ResDto 结尾，放于 bean.dto 包

mongoDB 实体，以 doc 结尾，放于 bean.doc 包

复杂业务实体，以 Bo 结尾，放于 bean.bo 包

其他数据实体，以 Do 结尾并冠以前缀，放于 bean.data 包，如

- 缓存实体，以 CacheDo 结尾
- es 实体，以 IdxDo 结尾
- 消息实体，以 MsgDo 结尾

另外，一些独立于业务之外的简单 bean，由于工具类、框架配置等的需要，可放于 model 包下，也可使用内部类方式

### 工具类的命名

工具类放于 util 包

一般使用单数的 Util 结尾，当工具类需要注入使用时，推荐以 Kit 结尾进行区分



