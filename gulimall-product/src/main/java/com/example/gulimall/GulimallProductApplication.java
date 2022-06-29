package com.example.gulimall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 想要远程调用服务
 * 1.需要引入open-feign
 * 2.编写一个接口，告诉spirngcloud需要调用的远程服务
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.example.gulimall.gulimallproduct.dao")
@EnableFeignClients(basePackages = "com.example.gulimall.gulimallproduct.feign")
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
