## 服务创建示例

### 创建模块

创建一个模块 uni-boot-xxx，继承自 uni-boot-dependencies

```xml

<parent>
    <artifactId>uni-boot-dependencies</artifactId>
    <groupId>com.github.cadecode</groupId>
    <version>2023.1.0</version>
    <relativePath>../dependencies/pom.xml</relativePath>
</parent>
```

该模块下创建对应的 api 模块 uni-boot-xxx-api 和 svc 模块 uni-boot-xxx-svc

api 模块供外部调用、DTO 共享，svc 模块提供基础服务

## 添加依赖

本项目子模块版本维护在根 pom，第三方模块版本维护在 uni-boot-dependencies

api 模块基本依赖：
```xml

<dependencies>
    <dependency>
        <groupId>net.dreamlu</groupId>
        <artifactId>mica-auto</artifactId>
        <scope>provided</scope>
    </dependency>
    <!-- rpc -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-loadbalancer</artifactId>
    </dependency>
</dependencies>
```

svc 模块基本依赖：
```xml
<!--cloud-->
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    <!--tomcat-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    <!--test-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <!--jasypt-->
    <dependency>
        <groupId>com.github.ulisesbocchio</groupId>
        <artifactId>jasypt-spring-boot-starter</artifactId>
    </dependency>

    <dependency>
        <groupId>com.github.cadecode</groupId>
        <artifactId>uni-boot-common-plugin-actuator</artifactId>
    </dependency>
    <!--基础框架包-->
    <dependency>
        <groupId>com.github.cadecode</groupId>
        <artifactId>uni-boot-framework-base</artifactId>
    </dependency>
</dependencies>
```

## 创建启动类

```java

@MapperScan("com.github.cadecode.**.mapper")
@EnableFeignClients("com.github.cadecode")
@EnableDiscoveryClient
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class UniXxxApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(UniExampleApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UniExampleApplication.class);
    }
}
```

## 配置文件

bootstrap.yml
```yaml
logging:
  file:
    path: /log/uni_boot_admin/xxx

spring:
  application:
    name: uni-boot-xxx
  cloud:
    nacos:
      server-addr: localhost:8848
      username: nacos
      password: nacos
      discovery:
        enabled: true
        namespace: uni_dev
        group: DEFAULT_GROUP
      config:
        enabled: true
        namespace: uni_dev
        group: DEFAULT_GROUP
        file-extension: yml
        shared-configs:
          - shared-config.${spring.cloud.nacos.config.file-extension}
          - shared-config-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}
```

logback-spring.xml
```xml
<!--略-->
```