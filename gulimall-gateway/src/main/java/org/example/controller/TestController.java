package org.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人:  @author zy
 * 创建时间:  2024年07月27日 22:39
 * 项目名称:  gulimall
 * 文件名称:  TestController
 * 文件描述:
 * 公司名称:  点出网络
 */
@RestController
@RefreshScope
public class TestController {

    @Value("${username.name}")
    private String name;

    @GetMapping("/test")
    public String test(){
        return name;
    }
}
