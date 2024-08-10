# 软件版本信息

| 软件名                | 版本号        |
|--------------------|------------| 
| java               | 1.8        |
| nodejs             | 14.21.3    |
| springboot         | 2.7.6      |
| springcloud        | 2021.0.8   | 
| springcloudalibaba | 2021.0.6.0 |

# 环境部署遇到的问题

1. nacos无法获取配置文件或配置文件始终为空问题（[Nacos Config] config[dataId=xxxx, group=xxxx] is empty）
   可以明确是nacos配置中心是有同名同分组同命名空间下的配置文件的， 但是nacos始终获取不到文件。经排查，在学习过程中引入了
   spring-cloud-starter-bootstrap 导致使用application.yml配置无法生效，在springcloudalibaba 2021.0.1.0 以上的版本可以不用配置
   bootstrap.yml故不需要引入这个依赖，如果使用的是
   bootstrap.yml配置方式才需要引用该依赖。参考文档地址：https://sca.aliyun.com/docs/2021/user-guide/nacos/advanced-guide/?spm=5176.29160081.0.0.74807a3cD5VGH7
2. 在配置springgataway时无法正确路由到nacos中注册的项目
   getway版本升级到了3.3以上的版本。 里面已经没有了loadbalancer需要单独添加对应的依赖信息

```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

# 性能检测工具jvisualvm

1. 下载地址 [https://visualvm.github.io/](https://visualvm.github.io/)
2. jvisualvm能干什么
   监控内存泄露、跟踪垃圾回收，执行时内存、CPU、线程分析等
   ![https://qn.3344love.cn/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20240801220114.png](img_1.png)

- 绿色: 正在运行中的线程
- 紫色: 休眠中的线程
- 黄色: 等待中的线程
- 橙色: 驻留的线程（线程池中空闲的线程）
- 蓝色: 监视，阻塞的线程，正在等待锁的线程

# 性能压测（本机配置 锐龙6800H 16G JVM  -Xms50m -Xmx400m）

| 压测内容           | 压测线程数 | 吞吐量/s | 90%响应时间 | 99%响应时间 |
|----------------|-------|-------|---------|---------|
| Nginx          | 50    | 2807  | 18      | 34      |
| SpringGateway  | 50    | 17498 | 4       | 10      |
| 简单服务           | 50    | 16952 | 4       | 38      |
| Gateway+简单服务   | 50    | 5738  | 15      | 37      |
| 简单服务 全链路       | 50    | 2093  | 28      | 34      |
| 首页一级菜单渲染       | 50    | 261   | 248        | 426     |
| 首页一级菜单渲染(开缓存)  | 50    | 301   | 173        | 199     |
| 三级分类数据获取       | 50    | 1     |47790         | 48196   |
| 首页全量数据获取       | 50    | 23    |2039         | 2076    |
| Nginx->Gateway | 50    |       |         |         |
中间件越多，性能损失越大，大部分都损失在网络交互上