# 软件版本信息
| 软件名         | 版本号   |
|-------------|-------| 
| java        | 1.8   |
| springboot  | 2.7.6 |
| springcloud | 2021.0.8 | 
| springcloudalibaba | 2021.0.6.0 | 

# 环境部署遇到的问题
1. nacos无法获取配置文件或配置文件始终为空问题（[Nacos Config] config[dataId=xxxx, group=xxxx] is empty）
可以明确是nacos配置中心是有同名同分组同命名空间下的配置文件的， 但是nacos始终获取不到文件。经排查，在学习过程中引入了 spring-cloud-starter-bootstrap 导致使用application.yml配置无法生效，在springcloudalibaba 2021.0.1.0 以上的版本可以不用配置 bootstrap.yml故不需要引入这个依赖，如果使用的是 bootstrap.yml配置方式才需要引用该依赖。参考文档地址：https://sca.aliyun.com/docs/2021/user-guide/nacos/advanced-guide/?spm=5176.29160081.0.0.74807a3cD5VGH7
2. 在配置springgataway时无法正确路由到nacos中注册的项目
getway版本升级到了3.3以上的版本。 里面已经没有了loadbalancer需要单独添加对应的依赖信息
```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```