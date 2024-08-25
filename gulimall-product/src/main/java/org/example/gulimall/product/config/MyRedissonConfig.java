package org.example.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建人:  @author zy
 * 创建时间:  2024年08月18日 16:49
 * 项目名称:  gulimall
 * 文件名称:  MyRedissonConfig
 * 文件描述:
 */
@Configuration
public class MyRedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://3344love.cn:6379").setPassword("zxcvbnm1234");
        return Redisson.create(config);
    }
}
